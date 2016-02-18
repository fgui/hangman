(ns hangman.views
  (:require [re-frame.core :as re-frame]
            [hangman.round :as round]))

(defn on-key-press [event]
  (re-frame/dispatch [:try-letter (char (.-which event))])
  )


(defn display-word [round]
  (clojure.string/join " "
                       (map #(if (= :no-letter %) "_" %)
                            (round/word-so-far round))))

(defn display-misses [round]
  (clojure.string/join ", " (round/misses round)))

(defn display-result [round]
  (if (round/won? round) "won" "lost"))


(defn score-component []
  (let [state (re-frame/subscribe [:state])
        round (re-frame/subscribe [:round])]
    (fn []
      [:div
       [:div "total score: "
             (round/accumulated-score @state)]
       [:div "score: "  (round/score @round)]]))
  )

(defn letter-color [guess letter]
  (if (= guess letter) :green :red)
  )

(defn show-letter [guess letter]
  [:span
   {:style {:color (letter-color guess letter)}}
   (str letter " ")]
  )

(defn display-resulting-word [round]
  (map show-letter (round/word-so-far round) (:word-to-guess round))
  )

(defn word-component[]
  (let [round (re-frame/subscribe [:round])]
    (fn []
      [:div (if (round/over? @round)
              (display-resulting-word @round)
              (display-word @round))]
      )
    )
  )

(defn main-panel []
  (let [round (re-frame/subscribe [:round])]
    (fn []
      [:div
       {:tab-index 0
        :on-key-press on-key-press}
       [score-component]
       [:table
        [:tbody
         [:td
          [:img {:src (str "img/Hangman-" (round/num-errors @round) ".png")
                 :width "100"
                 :style {:border "1px solid black"} }]]
         [:td
          [:table
           [:tbody
            [:tr [:td "Word:"] [:td [word-component]]]
            [:tr [:td "Misses:"] [:td (display-misses @round)]]
            (when (round/over? @round)
              (list [:tr [:td "Status:"] [:td (display-result @round)]]
                    [:tr [:td {:colspan 2}
                          [:button
                           {:on-click #(re-frame/dispatch [:new-round])}
                           "play again"]]])
              )]]]]]])))
