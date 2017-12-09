(ns levyraati.app.results
  "Controls levyraati result information."
  (:require [levyraati.app.state :as state]
            [levyraati.server :as server]))

;"participant": "Lotta",
;"artist": "Rotta",
;"title": "Vesinokkaeläinräppi",
;"url": "https://www.youtube.com/watch?v=l8v8USH-ISk",
;"points":[
;          {"name": "Ulle" , "value": 1},
;          {"name": "Dille" , "value": 2},
;          {"name": "Lulle" , "value": 3},
;          {"name": "Kalle" , "value": 4}],
;"ordinal": 4,
;"interesting-artist": false,
;"best-explanation": false,
;"weird-song":false

(defn- all-results [app results]
       (assoc-in app [:all-results] results))

(defn- show-results [app results]
  (assoc-in app [:show-results] results))

(defn- results-by-participant [app participant results]
  (assoc-in app [:results-by-participant participant] results))

(defn- results-top-three [app results]
       (assoc-in app [:results-top-three] results))

(defn- results-bottom-three [app results]
       (assoc-in app [:results-top-three] results))

(defn- set-participants [app participants]
  (assoc-in app [:participants] participants))

(defn- load-results-by-participant! [{:keys [results] :as app} server-get-fn! participant-name]
  (let [participant-result (some #(when (= (:id %) participant-name) %) results)]
    (server-get-fn! results)
    (-> app
        (assoc :participant participant-name)
        (assoc-in [:results-by-participant participant-name] :loading))))

(defn select-result-for-participant! [participant-name app]
  (state/update-state!
    load-results-by-participant! app
    (fn [participant-name]
      (server/get! (str "/participants/" (:id participant-name))
                   {:on-success #(state/update-state! results-by-participant participant-name %)}))
    participant-name))

(defn load-participants! []
  (server/get! "/participants" {:on-success #(state/update-state! set-participants %)}))


(defn load-results! []
  (server/get! "/results" {:on-success #(state/update-state! show-results %)
                           :on-failure (js/console.log "load-results! epäonnistui")}))

(defn load-interesting-artist! []
  (server/get! "/interesting" {:on-failure #(state/update-state! show-results %)
                                       :on-success #(state/update-state! show-results %)
                                       :on-change (js/console.log "load-interesting-artist! epäonnistui")}))
(defn load-weird-song! []
  (server/get! "/weird" {:on-success #(state/update-state! show-results %)
                                 :on-failure (js/console.log "load-weird-song! epäonnistui")}))

(defn load-best-explanation! []
  (server/get! "/best" {:on-success #(state/update-state! show-results %)
                                :on-failure (js/console.log "load-best-explanation! epäonnistui")}))

(defn load-bottom-three! []
  (server/get! "/bottom-three" {:on-success #(state/update-state! show-results %)
                                        :on-failure (js/console.log "load-bottom-three! epäonnistui")}))

(defn load-top-three! []
  (server/get! "/top-three" {:on-success #(state/update-state! show-results %)
                                     :on-failure (js/console.log "load-top-three! epäonnistui")}))

(defn load-all-results! []
  (server/get! "/all-results" {:on-success #(state/update-state! all-results %)
                                       :on-failure (js/console.log "load-all-results! epäonnistui")}))

