(ns hangman.views
  (:require [re-frame.core :as re-frame]
            [hangman.game :as game]))

(defn on-key-press [event]
  (re-frame/dispatch [:try-letter (char (.-which event))])
  )


(defn display-word [round]
  (clojure.string/join " "
                       (map #(if (= :no-letter %) "_" %)
                            (game/word-so-far round))))

(defn display-misses [round]
  (clojure.string/join ", " (game/misses round)))

(defn display-result [round]
  (if (game/won? round) "won" "lost"))


(defn score-component []
  (let [state (re-frame/subscribe [:state])
        round (re-frame/subscribe [:round])]
    (fn []
      [:div
       [:div "total score: "
             (game/accumulated-score @state)]
       [:div "score: "  (game/score @round)]]))
  )

(defn show-letter [guess letter]
  [:span
   {:style (when-not (= guess letter) {:color :red})} letter]
  )

(defn mark-misses [round]
  (map show-letter (game/word-so-far round) (:word-to-guess round))
  )

(defn word-component[]
  (let [round (re-frame/subscribe [:round])]
    (fn []
      [:div (if (game/lost? @round)
              (mark-misses @round)
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
          [:img {:src (str "img/Hangman-" (game/num-errors @round) ".png")
                 :width "100"
                 :style {:border "1px solid black"} }]]
         [:td
          [:table
           [:tbody
            [:tr [:td "Word:"] [:td [word-component]]]
            [:tr [:td "Misses:"] [:td (display-misses @round)]]
            (when (game/game-over? @round)
              (list [:tr [:td "Status:"] [:td (display-result @round)]]
                    [:tr [:td {:colspan 2}
                          [:button
                           {:on-click #(re-frame/dispatch [:new-round])}
                           "play again"]]])
              )]]]]]])))
