(defproject simple-mud "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :source-paths ["src/main/clojure"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/core.async "0.1.0-SNAPSHOT"]
                 [clj-logging-config "1.9.10"]
                 [org.clojure/tools.logging "0.2.3"]
                 [server-socket "1.0.0"]
                 [the/parsatron "0.0.4"]]
  :repositories {"sonatype-oss-public"
                 "https://oss.sonatype.org/content/groups/public/"}
  :main simple-mud.core)
