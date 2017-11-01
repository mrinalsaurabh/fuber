(ns fuber-api.test.integration.data.cabs
  (:require [clojure.test :refer :all] 
            [fuber-api.data.cabs :as cabs]
            [fuber-api.test.utils.exception :refer [is-type?]]))

(deftest test-update-cab-location
  (testing "should update the correct cab"
        (let [expected-data [{:number "1", :driver-name "A" :color "Pink" :location {:latitude 11.5 :longitude 13.5}}
                            {:number "2", :driver-name "B" :color "Pink" :location {:latitude 23.5 :longitude 32.5}}
                            {:number "3", :driver-name "C" :color "Green" :location {:latitude 11.5 :longitude 13.5}}
                            {:number "4", :driver-name "D" :color "Pink" :location {:latitude 11.5 :longitude 13.5}}
                            {:number "5", :driver-name "E" :color "Pink" :location {:latitude 11.5 :longitude 13.5}}]]
          (cabs/update-cab-location "2" 32.5 23.5) 
          (is (= expected-data @cabs/all-cabs)))))

(deftest test-get-nearest-free-cabs
  (cabs/update-cab-location "1" 1 1)
  (cabs/update-cab-location "2" 2 2)
  (cabs/update-cab-location "3" 3 3)
  (cabs/update-cab-location "4" 4 4)
  (cabs/update-cab-location "5" 5 5)
  (testing "should give the minimum distance cab"
    (let [expected-cab-1 {:number "1", 
                        :driver-name "A" 
                        :color "Pink" 
                        :location {:latitude 1 :longitude 1}}
          expected-cab-2 {:number "2", 
                          :driver-name "B" 
                          :color "Pink" 
                          :location {:latitude 2 :longitude 2}}
          expected-cab-3 {:number "3", 
                          :driver-name "C" 
                          :color "Green" 
                          :location {:latitude 3 :longitude 3}}]
      (is (= expected-cab-1 (cabs/get-nearest-free-cabs [] 1 1)))
      (is (= expected-cab-2 (cabs/get-nearest-free-cabs [] 1.75 1.75))
      (is (= expected-cab-3 (cabs/get-nearest-free-cabs ["1", "2"] 1.75 1.75))))))
    (testing "should give minimum distance pink cab for hippie rides"
      (let [expected-cab {:number "4", 
                            :driver-name "D" 
                            :color "Pink" 
                            :location {:latitude 4 :longitude 4}}] 
        (is (= expected-cab (cabs/get-nearest-free-cabs ["1", "2"] 1.75 1.75 true))))))