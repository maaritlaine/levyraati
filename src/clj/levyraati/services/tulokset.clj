(ns levyraati.services.tulokset
  (:require [levyraati.components.http :refer [publish! transit-response]]
            [com.stuartsierra.component :as component]
            [compojure.core :refer [routes GET POST]]
            [clojure.data.json :as json]
            [clojure.string :refer [split] :as str]
            ))



(defn genre-jakauma []
  (json/read-str (slurp "resources/genret.json")
                 :key-fn keyword))

(defn tulokset []
  (json/read-str (slurp "resources/tulokset.json")
                 :key-fn keyword))

(defn kappale-jonka-kuunteleminen-kesti-pisimpaan-ehka-liiankin-pitkaan-voisi-ajatella [tulokset]
  (filter #(= (:longest-bastard %) true) tulokset))

(defn lyhyt [tulokset]
  (filter #(= (:neat-and-short %) true) tulokset))

(defn vaihtoehtoisin-viritys [tulokset]
  (filter #(= (:most-out-of-tune %) true) tulokset))

(defn kiinnostavin-artisti [tulokset]
  (filter #(= (:longest-bastard %) true) tulokset))

(defn oudoin-piisi [tulokset]
  (filter #(= (:weird-song %) true) tulokset))

(defn paras-nimi [tulokset]
  (filter #(= (:best-name %) true) tulokset))

(defn paras-selitys [tulokset]
  (filter #(= (:best-explanation %) true) tulokset))

(defn kolme-parasta
  ([tulokset](take 3 (sort-by :ordinal tulokset)))
  ([tulokset lopusta?](take 3 (reverse(sort-by :ordinal tulokset)))))


(defn n-parasta
  ([n tulokset](take n (sort-by :ordinal tulokset)))
  ([n tulokset lopusta?](take n (reverse(sort-by :ordinal tulokset)))))


  ;; TODO: funktio toimii mutta on ihan sekopää.
  (defn kaikki-tulokset [tulokset]
    (let [new-tulokset (map
                        (fn [res]
                          (assoc res :title (second (split (get res :artist) #" - ")))

                          ) tulokset)
          newer-tulokset (map
                          (fn [res]
                            (assoc res :artist (first (split (get res :artist) #" - ")))
                            ) new-tulokset)]
      newer-tulokset))

  ;(def query "some-value")
  ;(def matching (filter #(and (< (:column1 %) query) (< query (:column2 %))) tulokset))

  ;(defn results-filtered-by-entry [entries]
  ;  "Returns a map of result details filtered by a vector of entries"
  ;  (map (fn [] (filter #(= (:participant %) "Helka") results)) entries)
  ;  )

