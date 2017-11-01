(ns fuber-api.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[fuber-api started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[fuber-api has shut down successfully]=-"))
   :middleware identity})
