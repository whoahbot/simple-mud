(ns simple-mud.core
  (:require [clojure.core.async :as async
             :refer [go chan >! <! >!! <!! close!]]
            [simple-mud.config :as config]
            [clojure.java.io :refer [reader writer]]
            [server.socket :refer [create-server]])
  (:gen-class))

(def player-input-channels
  "An atom containing a map of username to a player's output channel."
  (atom {}))

(defn handler
  "Main handler function."
  [in out]
  (binding [*in* (reader in)
            *out* (writer out)
            *err* (writer System/err)]
    (let [player-output-channel (chan)
          player-input-channel (chan)]
      (go (>! player-output-channel "Welcome to simple-mud!"))
      (go (<! player-input-channel (read-line)))
      (try
        (while true
          (println (<!! (go (<! player-output-channel))))
          (.flush *err*))
        (finally 
          (close! player-output-channel)
          (close! player-input-channel))))))

(defn -main
  [conf & args]
  (let [mud-config (config/load-config conf)]
    (defonce server (create-server (:port mud-config) handler))))
