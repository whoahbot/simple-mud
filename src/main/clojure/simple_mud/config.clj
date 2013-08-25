(ns simple-mud.config
  (:require [clojure.java.io :as io]))

(defn slurp-config
  [file]
  (load-string
   (slurp (io/as-file file))))

(defn load-config
  "Load configuration from FILE"
  ([file]
     (try
       (slurp-config file)
       (catch RuntimeException e
         (println "CRITICAL: Failed to load config file.")
         (throw e)))))
