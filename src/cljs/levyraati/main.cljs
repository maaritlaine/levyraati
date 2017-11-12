(ns levyraati.main
  "Main entrypoint for the levyraati frontend."
  (:require [reagent.core :as r]
            [cljsjs.material-ui]
            [cljs-react-material-ui.core :refer [get-mui-theme color]]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [levyraati.app.state :as state]
            [levyraati.app.results :as results]))


(defn listaus [e! jutut]
  [:ul
   (for [juttu jutut]
     [:li [:a {:on-click #(e! (->ValitseJuttu juttu))} juttu]])])

(defn levyraati [app]
  [ui/mui-theme-provider
   {:mui-theme (get-mui-theme
                 {:palette {
                            :primary1Color  (color :pinkA200),
                            :primary2Color (color :pinkA400),
                            :accent2Color (color :pink50),
                            :pickerHeaderColor (color :pinkA200)
                            }} )}

   [:div {:style {:font-size 80
                  :font-family :Poppins
                  :text-align :center
                  :padding 10
                  :margin-bottom 5
                  }}
     "Töissä Solitassa"

[:div  {:style {
                :font-size 40
                :font-family :Lobster
                :text-align :center
                }}
 "Oulun levarihommat"
 ]
    [:div  {:style {:background-color  (color :pinkA200)
                    :height 40
                    :font-size 20
                    :font-family :Poppins
                    :font-weight :bold
                    :font-color (color :white)
                    :padding 5
                    :text-align :center

                    }}
     "18. kierros"
     ]


    [ui/paper

     ;; Participant selection
     (when-not (= :loading (:participants app))
       [ui/select-field {:floating-label-text "Select participant"
                         :value (:id (:participant app))
                         :on-change (fn [evt idx value]
                                      (results/select-result-for-participant! value))}
        (for [{:keys [id name] :as participant} (:participants app)]
          ^{:key id}
          [ui/menu-item {:value id :primary-text name}])])


     [ui/raised-button {:label "Click me"
                        :icon (ic/social-group)
                        :on-click #(println "clicked")}]]]])


(defn main-component []
  [levyraati @state/app])

(defn ^:export main []
  (results/load-participants!)
  (r/render-component [main-component] (.getElementById js/document "app")))

(defn ^:export reload-hook []
  (r/force-update-all))
