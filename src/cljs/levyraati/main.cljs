(ns levyraati.main
  "Main entrypoint for the levyraati frontend."
  (:require [reagent.core :as r]
            [cljsjs.material-ui]
            [cljs-react-material-ui.core :refer [get-mui-theme color]]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [levyraati.app.state :as state]
            [levyraati.app.results :as results]
            [levyraati.result :as result-ui]
            [levyraati.entry :as entry-ui]))

(enable-console-print!)

(defn listaus [e! jutut]
  [:ul
   (for [juttu jutut]
     [:li [:a {:on-click #(e! (->ValitseJuttu juttu))} juttu]])])

(defn current-page []
  (get @state/app :page))


(defn current-active-step []
  (get @state/app :active-step))

(defn current-results []
  (get @state/app :show-results))


(defn title []

  [:div
   [:div {:style {:font-size 80
                  :font-family :Poppins
                  :text-align :center
                  :padding 10
                  :margin-bottom 5
                  }}
    "Töissä Solitassa"]

   [:div {:style {
                  :font-size 40
                  :font-family :Lobster
                  :text-align :center
                  }}
    "Oulun levarihommat"]

   [:div {:style {:background-color (color :red600)
                  :height 40
                  :font-size 20
                  :font-family :Didact+Gothic
                  :font-weight :bold
                  :text-color (color :white)
                  :padding 5
                  :text-align :center

                  }}
    "18. kierros"]]

  )

;;TODO: refaktoroi current-page liittyvät tiedot yhteen mäppiin
(defn page-by-active-step [active-step]
  (case active-step
    1 :splash
    2 :results-most-interesting-artist
    3 :results-weirdest-song
    4 :results-best-explanation
    5 :results-greatest-variance-in-points
    6 :results-lest-variance-in-points
    7 :results-bottom-three
    8 :results-top-three
    9 :all-results
    100 :all-entries
    110 :entry-by-participant
    :default))

;;TODO: stuff to refactor here - default function?
(defn function-by-active-step [active-step app]
  (case active-step
    1 (results/load-results!)
    2 (results/load-interesting-artist!)
    3 (results/load-weird-song!)
    4 (results/load-best-explanation!)
    5 (results/load-all-results!)                       ;; TODO: :results-greatest-variance-in-points
    6 (results/load-all-results!)                       ;; TODO: :results-lest-variance-in-points
    7 (results/load-bottom-three!)
    8 (results/load-top-three!)
    9 (results/load-all-results!)
    (results/load-results!)
    )
  )

(defn forward-button-text [page]
  (case page
    :splash "Hämärin artisti"
    :results-most-interesting-artist "Oudoin piisi"
    :results-weirdest-song "Paras selitys"
    :results-best-explanation "Hämmentävin yritys"
    :results-greatest-variance-in-points "Yhteisin sävel"
    :results-lest-variance-in-points "Parhaat pohjanoteeraukset"
    :results-bottom-three "Parhaat viisut"
    :results-top-three "Kaikki tulokset"
    :all-results "X"

    :all-entries "X"
    :entry-by-participant "X"

    "Seukki"))


(defn back-button-text [page]
  (case page
    :splash "X"
    :results-most-interesting-artist "Aloita alusta"
    :results-weirdest-song "Ihmein tyyppi"
    :results-best-explanation "Hämyin kappale"
    :results-greatest-variance-in-points "Maistuvin selittely"
    :results-lest-variance-in-points "Hajauttavin kappale"
    :results-bottom-three "Yhdistävin kappale"
    :results-top-three "Bottom 3!"
    :all-results "Top 3!"

    :all-entries "Kaikki tulokset"
    :entry-by-participant "X"

    "Back"))


(defn navigate [direction app]
  (state/update-state! (fn [app]
                         (assoc app :active-step (direction (current-active-step) 1))))
  (state/update-state! (fn [app]
                         (assoc app :page (page-by-active-step (current-active-step)))))
   (function-by-active-step (current-active-step) app))

(defn navigate-forward [app]
  (navigate + app))

(defn navigate-backwards [app]
  (navigate - app))

(defn navigation-header []
  [:div
   [ui/stepper {:active-step (- (current-active-step) 1)}
    [ui/step
     [ui/step-label "1"]]
    [ui/step
     [ui/step-label "2"]]
    [ui/step
     [ui/step-label "3"]]
    [ui/step
     [ui/step-label "4"]]
    [ui/step
     [ui/step-label "5"]]
    [ui/step
     [ui/step-label "6"]]
    [ui/step
     [ui/step-label "7"]]
    [ui/step
     [ui/step-label "8"]]]]
  )

(defn navigation-footer [app]
  [:div
   [ui/flat-button {:label (back-button-text (current-page))
                    :on-click (fn [app] (navigate-backwards app))}]
   [ui/flat-button {:label (forward-button-text (current-page))
                    :on-click (fn [app] (navigate-forward app))}]
                               ;TODO: default
   ])

(defn splash []
  [:div {:style {:font-size 80
                 :font-family :Poppins
                 :text-align :center
                 :padding 10
                 :margin-bottom 5
                 }}
   "SPLASHHHH!!!!"]
  )


(defn testi [step]
  (println (str "Saatiin askel " step))
  (println (current-page))
  (case step
    1 [:div "yksi"]
    2 [:div "kaksi"]
    3 [:div "kolme"]
    4 [:div "neljä"]
    5 [:div "viisi"]
    [:div "joku muu"])
  )



(defn canvas [current-active-step results]
  (case (page-by-active-step current-active-step)
    :splash [splash]
    :all-results [result-ui/all-results-page]
    :results-top-three [result-ui/top-three-page results]
    :results-bottom-three [result-ui/bottom-three-page results]
    :results-best-explanation [result-ui/best-explanation-page results]
    :results-most-interesting-artist [result-ui/interesting-artist-page results]
    :results-weirdest-song [result-ui/weird-song-page results]
    :results-greatest-variance-in-points [result-ui/most-variation-in-points-page results]
    :results-lest-variance-in-points [result-ui/least-variation-in-points-page results]

    1 [splash]
    2 [result-ui/all-results-page]
    3[result-ui/top-three-page results]
    4[result-ui/bottom-three-page results]
    5[result-ui/best-explanation-page results]
    6[result-ui/interesting-artist-page results]
    7[result-ui/weird-song-page results]
    8[result-ui/most-variation-in-points-page results]
    9 [result-ui/least-variation-in-points-page results]
    [:div "Ei löytyny mitään"]
    )
  )

(defn levyraati [app]

  ;(println (str "********************** CURRENT STATE " app ))

  [ui/mui-theme-provider {mui-theme (get-mui-theme)}
   [ui/paper
    [title]
;    [:div "HIHI" (println (get app [:show-results ]))]

[ui/table
 [ui/table-body {:display-row-checkbox false}
  (for [{:keys [ordinal name artist participant] :as item} (get-in app [:show-results])] ;;TODO: Mitähän täällä cartissa olikaan nää
    ^{:key ordinal}

    [ui/table-row
     [ui/table-row-column name]
     [ui/table-row-column artist]
     [ui/table-row-column participant]
     [ui/table-row-column ordinal]])]]

    [navigation-header]
    [canvas (current-active-step) (get-in app [:show-results])]
    ;[testi (current-active-step)]
    [navigation-footer app]
  ]
 ]



    ;[ui/mui-theme-provider
  ; {:mui-theme (get-mui-theme
  ;               {:palette {:text-color (color :green600)}})}

  ;{:mui-theme (get-mui-theme
  ;              {:palette {
  ;                         :primary1Color (color :pinkA200),
  ;                         :primary2Color (color :pinkA400),
  ;                         :accent2Color (color :pink50),
  ;                         :pickerHeaderColor (color :pinkA200)
  ;                         }})}

  ;
  ;[ui/toggle
  ; {:style {:background-color (color :white)
  ;          :height 30
  ;          :font-size 20
  ;          :font-family :Poppins
  ;          :font-weight :bold
  ;          :text-color (color :pink500)
  ;
  ;          }
  ;  :label "Variation score"
  ;  }]
  ;
  ;
  ;[ui/paper {:style {:circle :true
  ;                   :zDepth 2
  ;                   :background-color (color :deep-orange600)}}
  ;
  ; ;; Participant selection
  ; (when-not (= :loading (:participants app))
  ;   [ui/select-field {:floating-label-text "Select participant"
  ;                     :value (:id (:participant app))
  ;                     :on-change (fn [evt idx value]
  ;                                  (results/select-result-for-participant! value))}
  ;    (for [{:keys [id name] :as participant} (:participants app)]
  ;      ^{:key id}
  ;      [ui/menu-item {:value id :primary-text name}])])
  ;
  ;
  ; [ui/raised-button {:label "Click me"
  ;                    :icon (ic/social-group)
  ;                    :on-click #(println "clicked")}]
  ; ]
  ; ]

  )



(defn main-component []
  [levyraati @state/app])

(defn ^:export main []
  (results/load-results!)
  (r/render-component [main-component] (.getElementById js/document "app")))

(defn ^:export reload-hook []
  (r/force-update-all))

