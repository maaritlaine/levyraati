(ns levyraati.services.results
  (:require [levyraati.components.http :refer [publish! transit-response]]
            [com.stuartsierra.component :as component]
            [compojure.core :refer [routes GET POST]]
            [clojure.data.json :as json]
            ))



(defn results []
  (json/read-str (slurp "resources/results.json")
                            :key-fn keyword))

(def query "some-value")
(def matching (filter #(and (< (:column1 %) query) (< query (:column2 %))) results))

(defn read-results []
  "Reads the results from the result.json file."
  (println "Get all the results "))

(defn results-top-three
  "Returns top three of the results"
  [results]
  {:first "Eka" :second "Toka" :third "Kolmas"}
  )

(defn results-bottom-three [results]
  "Returns bottom three of the results"
  {:last "Vika" :second "Toka" :third "Kolmas"}
  )

(defn results-by-variance [results]
  "Returns results with most and least variance"
  {:most-variance "QKHTLS" :least-variance "eeeeeee"}
  ;(vec (sort-by :points-sum (assoc results :points-sum (reduce results :))))
  )

(defn results-by-participant [participant results]
   (filter #(= (:participant %) participant) results)
)

(defn matching-result-by-id
  [id results]
  (println (filter #(= (:id %) id) results))
  )

(defn results-filtered-by-entry [entries]
  "Returns a map of result details filtered by a vector of entries"
  (map (fn [] (filter #(= (:participant %) "Helka") results)) entries)
  )

(defn fetch-participants []
  "Returns a vecor of participants."
  ["Lotta" "Helka" "Maarit"])


(defrecord ResultsService []
  component/Lifecycle
  (start [{:keys [http] :as this}]
    (assoc this ::routes
                (publish! http
                          (routes
                            (GET "/results" []
                                 (transit-response
                                   (read-results)))
                            (GET "/results/topthree" []
                              (transit-response
                                (results-top-three results)))
                            (GET "/results/bottomthree" []
                                 (transit-response
                                   (results-bottom-three results)))
                            (GET "/results/:participant" [participant]
                                 (transit-response
                                   (results-by-participant participant results)))
                            (GET "/results/variance" []
                                 (transit-response
                                   (results-by-variance results)))
                            (GET "/participants" []
                                 (transit-response
                                   (read-results)))
                            ))))
  (stop [{stop ::routes :as this}]
    (stop)
    (dissoc this ::routes)))
