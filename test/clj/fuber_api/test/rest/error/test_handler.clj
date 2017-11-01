(ns fuber-api.test.rest.error.test-handler
  (:require [compojure.core :refer [routes]]
            [fuber-api.middleware :as middleware]
            [fuber-api.env :refer [defaults]]
            [fuber-api.test.rest.error.test-api :refer [service-routes]]))

(def app-routes
  (routes #'service-routes))

(defn app [] (middleware/wrap-base #'app-routes))
