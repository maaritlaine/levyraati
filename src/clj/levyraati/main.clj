(ns levyraati.main
    "Main ns for levyraati. Starts everything up."
    (:gen-class)
    (:require [com.stuartsierra.component :as component]
      [levyraati.components.http :as http]
      [levyraati.services.results :as results]
      ))

(def system nil)

(defn levyraati-system [settings]
      (component/system-map
        :http (http/create-http-server (get-in settings [:http :port]))
        :results (component/using (results/->ResultsService) [:http])
        ))


(defn -main [& args]
      (alter-var-root #'system
                      (constantly
                        (-> "settings.edn"
                            slurp read-string
                            levyraati-system
                            component/start-system))))