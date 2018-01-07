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

  ; sortable table
 ; avatar / participant - saako haettua solitan intrasta jotain vai tekiskö jotku ikoniavatarit tai esittäjän mukaan
 ; upotettu youtube video




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

  ; progress ja hiadastus
  ; togglet
  ; yks kerrallaan card
  ; kaikki yhdessä taulukossa

  [:div "TOP 3"
   ])

(defn bottom-three-page [show-result]

  ; progress ja hiadastus
  ; togglet
  ; yks kerrallaan card
  ; kaikki yhdessä taulukossa


  [:div {:style {:font-size 20
                 :font-family :Poppins
                 :text-align :center
                 :padding 10
                 :margin-bottom 5
                 }}
   "Bottom 3"]

  [:div
[ui/toggle {:label "Kolmanneksi viimeinen"}]
[ui/toggle {:label "Tokavika"}]


[ui/toggle {:label "Kierroksen väärinymmärretyin"
            :label-style { :color "green"}
            :thumb-style {}
            :track-style {}
            :thumb-switched-style {}
            :track-switched-style {}
            }]

   [ui/toggle {:label "Kierroksen väärinymmärretyin"
            :label-style { :color "green"}
            :thumb-style {}
            :track-style {}
            :thumb-switched-style {}
            :track-switched-style {}
            }]

   :text-color (color :pink500)

   ])


(defn best-explanation-page [show-result]

  ; yks kerrallaan card

  [:div "Paras selitys"
   ])

(defn interesting-artist-page [show-result]

  ; yks kerrallaan card

  [:div "Vänkä artisti"
   [ui/table
    [ui/table-body {:display-row-checkbox false}
     (for [{:keys [ordinal title artist participant] :as item} show-result] ;;TODO: Mitähän täällä cartissa olikaan nää
       ^{:key ordinal}
       [ui/table-row
        [ui/table-row-column title]
        [ui/table-row-column artist]
        [ui/table-row-column participant]
        [ui/table-row-column ordinal]])]]
   ]

;[ui/card
; [ui/card-header {:title (get-in show-result :artist "NOT FOUND")
;                  :subtitle "Subtitle"
;                  ;:avatar "images/image.jpg"
;                  }]
; [ui/card-text  "hfhfhf" "fwefwfewfew"]]


;  <link href="https://fonts.googleapis.com/css?family=Chelsea+Market|Creepster|Didact+Gothic|Eater|Goblin+One|Gruppo|Henny+Penny|Julius+Sans+One|Lalezar|Libre+Barcode+39+Extended|Mystery+Quest|Open+Sans|Russo+One|Spectral+SC" rel="stylesheet">

  )

(defn weird-song-page [show-result]

  ; yks kerrallaan card

  [:div "Outo piisi"
   ])

(defn greatest-variance-page [show-result]

  ; yks kerrallaan card

  [:div "Suurin varianssi"
   ])

(defn least-variance-page [show-result]

  ; yks kerrallaan card

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


