(ns levyraati.services.entries
  (:require [levyraati.components.http :refer [publish! transit-response]]
            [com.stuartsierra.component :as component]
            [compojure.core :refer [routes GET POST]]
            [clojure.data.json :as json]
            ))


;;TODO: tallenna atomiin?
(defn- results []
  (json/read-str (slurp "resources/results.json")
                 :key-fn keyword))


(defn all-entries []
  (results)
  )

(defn entry-by-participant
  [participant]
  (filter #(= (:participant %) participant) (all-entries)))

(defn participants []
  (map #(% :participant) (all-entries)))


(def query "some-value")
(def matching (filter #(and (< (:column1 %) query) (< query (:column2 %))) results))

(defn read-entries []
  "Reads the entries from the result.json file. Does not show results."
  (println "Get all the entries without results and specifics."))


;(defn entry-by-participant [results]
;  "Returns an entry of the participant"
;  (println "Get one entry with specifics. And a result - up to UI to show or not.")
;  ;(vec (sort-by :points-sum (assoc results :points-sum (reduce results :))))
;  )

;(defn results-filtered-by-entry [entries]
;  "Returns a map of result details filtered by a vector of entries"
;  (map (fn [] (filter #(= (:participant %) "Helka") results)) entries)
;  )



(defrecord ResultsService []
  component/Lifecycle
  (start [{:keys [http] :as this}]
    (assoc this ::routes
                (publish! http
                          (routes
                            (GET "/entries" []
                              (transit-response
                                (read-entries)))
                            (GET "/entries/:participant" []
                              (transit-response
                                (entry-by-participant results)))
                            ))))
  (stop [{stop ::routes :as this}]
    (stop)
    (dissoc this ::routes)))
