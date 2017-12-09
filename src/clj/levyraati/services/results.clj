(ns levyraati.services.results
  (:require [levyraati.components.http :refer [publish! transit-response]]
            [com.stuartsierra.component :as component]
            [compojure.core :refer [routes GET POST]]
            [clojure.data.json :as json]
            ))



(defn results []
  (json/read-str (slurp "resources/results.json")
                            :key-fn keyword))


(defn all-results []
  (results)
  )

;; TODO: refactor - take results as a paramter
(defn top-three []
  (println "GET top-three")
  (take 3 (sort-by :ordinal (all-results))))

(defn bottom-three []
  (println "GET bottom-three")
  (take 3 (reverse (sort-by :ordinal (all-results)))))

(defn top-n [n]
  (take n (sort-by :ordinal (all-results))))

(defn bottom-n [n]
  (take n (reverse (sort-by :ordinal (all-results)))))

(defn best-explanation []
  (println "GET best-explanation")
  (filter #(= (:best-explanation %) true) (all-results)))

(defn interesting-artist []
  (println "GET interesting-artist")
  (filter #(= (:interesting-artist %) true) (all-results)))

(defn weird-song []
  (println "GET weird-song")
  (filter #(= (:weird-song %) true) (all-results)))

(defn result-by-participant [participant]
  (filter #(= (:participant %) participant) (all-results))
  )


;(def query "some-value")
;(def matching (filter #(and (< (:column1 %) query) (< query (:column2 %))) results))

;(defn results-filtered-by-entry [entries]
;  "Returns a map of result details filtered by a vector of entries"
;  (map (fn [] (filter #(= (:participant %) "Helka") results)) entries)
;  )

;:TODO: refactor to use :all-results from @state/app as data source (as opposed to json file)
(defrecord ResultsService []
  component/Lifecycle
  (start [{:keys [http] :as this}]
    (assoc this ::routes
                (publish! http
                          (routes
                            (GET "/results" []
                                 (transit-response
                                   (all-results)))
                            (GET "/interesting" []
                                 (transit-response
                                   (interesting-artist)))
                            (GET "/weird" []
                                 (transit-response
                                   (weird-song)))
                            (GET "/best" []
                                 (transit-response
                                   (best-explanation)))
                            (GET "/bottom-three" []
                                 (transit-response
                                   (bottom-three)))
                            (GET "/top-three" []
                              (transit-response
                                (top-three)))
                            (GET "/all-results" []
                              (transit-response
                                (all-results)))
                            (GET "/results/participant" [participant]
                                 (transit-response
                                   (result-by-participant participant)))
                            (GET "/results/variance" []
                                 (transit-response
                                   (all-results)))
                            (GET "/participants" []
                                 (transit-response
                                   (all-results)))
                            ))))
  (stop [{stop ::routes :as this}]
    (stop)
    (dissoc this ::routes)))
