(ns simple-mud.core
  (:require [clojure.core.async :as async
             :refer [go chan >! <! >!! <!! close!]]
            [simple-mud.config :as config]
            [clojure.java.io :refer [reader writer]]
            [server.socket :refer [create-server]])
  (:gen-class))

(def player-input-channels
  "An atom containing a map of (keyword username) -> input channel."
  (atom {}))

(defn handler
  "Main handler function."
  [in out]
  (binding [*in* (reader in)
            *out* (writer out)
            *err* (writer System/err)]
    (let [player-output-channel (chan)
          player-input-channel (chan)]
      (println "Welcome to simple-mud!")
      (println "Please enter your name: ")
      (swap! player-input-channels assoc (keyword (read-line)) player-input-channel)
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
