(ns levyraati.entry
  "Levyraati entry component"
  (:require [reagent.core :as r]
            [cljsjs.material-ui]
            [cljs-react-material-ui.core :refer [get-mui-theme color]]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [levyraati.app.entries :as entries]))


; Javascriptista clojureksi muuntuu näin
;(js -> clj %)

; Consolessa näkymään kamaa saa näin, ylemmällä pystyy myös kutsumaan konsolista siten hip.
; (ase js/window "hip " %)
; js/console.log toimii myös

;[ui/table {:on-row-selection #(js/alert (str "valittu: " (inc (first %)) ))}


(defn entrylist [products]
  "Creates table of entries."
  (if (= :loading entries)

    ;; participant
    ;; artist / title
    ;; explanation score / embedded youtube
    ;; trivia about the artist, song or the video
    ;; notes about the score (ordinal, variation, most suprising) in icon format

    [ui/table
     [ui/table-header "Lotta"]
     [ui/table-row
      [ui/table-row-column "Teräsvilla"]
      [ui/table-row-column "Rakennetaan rakennetaan!"]
      ]
     [ui/table-row
      [ui/table-row-column "Teräsvilla on rakennusmateriaali. Bändissä on paljon rakennusalan ammattilaisia. Siksi sopii meille."]
      [ui/table-row-column "Katso video!"]
      ]
     [ui/table-row
      [ui/table-row-column "Todellisuudessa kaikki jäsenet ovat virkamiehiä."]
      ]
     [ui/table-footer "Eniten variaatiota!"]
     ]

    ))

;;https://stackoverflow.com/questions/39188405/how-to-use-video-html-tag-instead-of-img-tag-in-material-ui-card-component