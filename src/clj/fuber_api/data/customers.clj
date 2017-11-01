(ns fuber-api.data.customers
  (:require [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [clojure.walk :as walk]
            [utils.json :as json-utils]))

(def all-customers 
    (atom
        [{:id "1", :name "AA"}
        {:id "2", :name "BB" }
        {:id "3", :name "CC" }
        {:id "4", :name "DD" }
        {:id "5", :name "EE" }]))

(defn add-customers [name]
    (swap! all-customers (fn [data]
                        (conj data 
                          {:id (+ 1 (read-string (:id (last data))))
                          :name name})))
    (:id (last @all-customers)))