(ns fuber-api.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [fuber-api.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[fuber-api started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[fuber-api has shut down successfully]=-"))
   :middleware wrap-dev})
