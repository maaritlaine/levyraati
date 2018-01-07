(ns levyraati.app.state
  "Defines the application state atom"
  (:require [reagent.core :as r]))

(defonce app (r/atom {:all-results {}
                      :show-results {}
                      :participants :loading ;; list of participants
                      :participant nil ;; the selected participant

                      :page :splash
                      :active-step 1
                      }))

(defn update-state!
  "Updates the application state using a function, that accepts as parameters
  the current state and a variable number of arguments, and returns a new state.

  (defn set-foo [app n]
     (assoc app :foo n))

  (update-state! set-foo 1)"
  [update-fn & args]
  (swap! app
         (fn [current-app-state]
           (apply update-fn current-app-state args))))
