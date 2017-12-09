(ns levyraati.result
      "Levyraati result UI component"
      (:require [reagent.core :as r]
                [cljsjs.material-ui]
                [cljs-react-material-ui.core :refer [get-mui-theme color]]
                [cljs-react-material-ui.reagent :as ui]
                [cljs-react-material-ui.icons :as ic]
                [levyraati.app.state :as state]
                [levyraati.app.results :as results]))



(defn toggle-top-three-page []

  [ui/toggle
   {:style {:background-color  (color :white)
            :width 200
            :height 30
            :font-size 20
            :font-family :Poppins
            :font-weight :bold
            :text-color (color :pink500)
            :text-align :left
            }
    :label "TOP 3" }])

(defn toggle-bottom-three-page []

  [ui/toggle
   {:style {:background-color  (color :white)
            :height 30
            :font-size 20
            :font-family :Poppins
            :font-weight :bold
            :text-color (color :pink500)

            }
    :label "BOTTOM 3" }])


(defn all-results-page []
  "Creates table of results."
  ;(if (= :loading results)

    ;; participant
    ;; artist / title
    ;; notes about the score (ordinal, variation, most suprising) in icon format

    [ui/table
     [ui/table-header "Lotta"]
     [ui/table-row
      [ui/table-row-column "Teräsvilla"]
      [ui/table-row-column "Rakennetaan rakennetaan!"]
      ]
     [ui/table-footer "Eniten variaatiota!"]
     ]

    )

(defn top-three-page [show-result]
  [:div "TOP 3"
   ])

(defn bottom-three-page [show-result]
  [:div "Bottom 3"
   ])


(defn best-explanation-page [show-result]
  [:div "Paras selitys"
   ])

(defn interesting-artist-page [show-result]
  [:div "Vänkä artisti"
   ])

(defn weird-song-page [show-result]
  [:div "Outo piisi"
   ])

(defn greatest-variance-page [show-result]
  [:div "Suurin varianssi"
   ])

(defn least-variance-page [show-result]
  [:div "Pienin varianssi"
   ])


(defn result-count-page [show-result]
  [:div "result-count-page 3"])

(defn average-points-page [show-result]
  [:div "average-points-page 3"])

(defn most-variation-in-points-page [show-result]
  [:div "most-variation-in-points-page 3"])

(defn least-variation-in-points-page [show-result]
  [:div "least-variation-in-points-page 3"])



(defn- result-count
  "Palauttaa entryjen määrän"
  []
          ;; tarvitsee jsonin
  )


