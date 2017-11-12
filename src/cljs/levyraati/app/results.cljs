(ns levyraati.app.results
  "Controls levyraati result information."
  (:require [levyraati.app.state :as state]
            [levyraati.server :as server]))

(defn- all-results [app results]
       (assoc-in app [:all-results] results))

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

(defn select-result-for-participant! [participant-name]
  (state/update-state!
    load-results-by-participant!
    (fn [participant-name]
      (server/get! (str "/participants/" (:id participant-name))
                   {:on-success #(state/update-state! results-by-participant participant-name %)}))
    participant-name))

(defn load-participants! []
  (server/get! "/participants" {:on-success #(state/update-state! set-participants %)}))
