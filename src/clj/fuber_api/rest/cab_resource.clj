(ns fuber-api.rest.cab-resource
  (:require [compojure.api.sweet :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]
            [fuber-api.services.cabs :as cabs-service]))

(defroutes routes
    (GET "/fuber/book-cab/:user-id/:latitude/:longitude/:hippie" [:as request]
      :path-params [user-id :- String latitude :- Number longitude :- Number hippie :- Boolean]
      :summary "Search cabs for a user"
      (let [cab (cabs-service/book-cabs user-id latitude longitude hippie)]
        (ok {:message cab})))
    (GET "/fuber/start-trip/:user-id/:latitude/:longitude" [:as request]
      :path-params [user-id :- String latitude :- Number longitude :- Number]
      :summary "Starts trip for the user"
          (ok {:message (cabs-service/start-trip user-id latitude longitude)}))
    (GET "/fuber/end-trip/:user-id/:latitude/:longitude" [:as request]
      :path-params [user-id :- String latitude :- Number longitude :- Number]
      :summary "Ends trip for the user"
          (ok {:message (cabs-service/end-trip user-id latitude longitude)})))
