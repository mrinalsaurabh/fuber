(ns user
  (:require [mount.core :as mount]
            fuber-api.core))

(defn start []
  (mount/start-without #'fuber-api.core/repl-server))

(defn stop []
  (mount/stop-except #'fuber-api.core/repl-server))

(defn restart []
  (stop)
  (start))


