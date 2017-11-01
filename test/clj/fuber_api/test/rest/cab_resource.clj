(ns fuber-api.test.rest.cab-resource
  (:require [clojure.test :refer :all]
            [fuber-api.rest.cab-resource :as cab-resource]
            [fuber-api.services.cabs :as cabs-service]
            [fuber-api.handler :refer [app]]
            [ring.mock.request :as r]
            [fuber-api.test.rest.json-util :as u]))

(deftest test-cab-resource
  (testing "should book a cab"
    (with-redefs [cabs-service/book-cabs (fn [user-id latitude longitude hippie] 
                                                              "Your cab has been booked")]
      (let [expected-data {:message "Your cab has been booked"}
            response ((app) (r/request :get "/api/fuber/book-cab/4/5/7/true"))
            actual-response (u/parse-body (:body response))]
        (is (= expected-data actual-response)))))
        
  (testing "should start a cab"
    (with-redefs [cabs-service/start-trip (fn [user-id latitude longitude] 
                                                              "Your trip has started.")]
      (let [expected-data {:message "Your trip has started."}
            response ((app) (r/request :get "/api/fuber/start-trip/4/5/7"))
            actual-response (u/parse-body (:body response))]
        (is (= expected-data actual-response)))))
        
  (testing "should end a cab trip"
    (with-redefs [cabs-service/end-trip (fn [user-id latitude longitude] 
                                                              "Your trip has ended.")]
      (let [expected-data {:message "Your trip has ended."}
            response ((app) (r/request :get "/api/fuber/end-trip/4/5/7"))
            actual-response (u/parse-body (:body response))]
        (is (= expected-data actual-response))))))
