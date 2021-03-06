(ns hangman.views
  (:require [re-frame.core :as re-frame]
            [hangman.round :as round]
            [hangman.i18n :as i18n]))

(defn display-word [round]
  (clojure.string/join " "
                       (map #(if (= :no-letter %) "_" %)
                            (round/word-so-far round))))

(defn display-misses [round]
  (clojure.string/join ", " (round/misses round)))

(defn display-result [round language]
  (if (round/won? round)
    (i18n/translate language :won)
    (i18n/translate language :lost)))

(defn score-component []
  (let [state (re-frame/subscribe [:state])
        round (re-frame/subscribe [:round])
        language (re-frame/subscribe [:language])]
    (fn []
      [:div
       [:div (str (i18n/translate @language :total-score) " " (round/accumulated-score @state))]
       [:div (str (i18n/translate @language :score) " "  (round/score @round))]])))

(defn letter-color [guess letter]
  (if (= guess letter) :green :red))

(defn show-letter [guess letter]
  [:span
   {:style {:color (letter-color guess letter)}}
   (str letter " ")])

(defn display-resulting-word [round]
  (map show-letter (round/word-so-far round) (:word-to-guess round)))

(defn word-component []
  (let [round (re-frame/subscribe [:round])]
    (fn []
      [:div (if (round/over? @round)
              (display-resulting-word @round)
              (display-word @round))])))

(defn choose-language [lang]
  (re-frame/dispatch [:new-language lang])
  false ;; prevent default event
)

(defn language-link-component [key]
  (let [language (re-frame/subscribe [:language])]
    (fn []
      [:a {:href "#" :on-click #(choose-language key)
           :style (when (= @language key) {:font-weight :bold})}
       (i18n/translate key :language-name)])))

(defn make-language-components []
  (->> i18n/dictionary
       keys
       (map #(vector language-link-component %))
       (interleave (repeat " / "))
       (drop 1)))

(defn main-panel []
  (let [round (re-frame/subscribe [:round])
        language (re-frame/subscribe [:language])]
    (fn []
      [:div
       [:div {:style {:float :right}} (make-language-components)]
       [score-component]
       [:table
        [:tbody
         [:td
          [:img {:src (str "img/Hangman-" (round/num-errors @round) ".png")
                 :width "100"
                 :style {:border "1px solid black"}}]]
         [:td
          [:table
           [:tbody
            [:tr [:td (i18n/translate @language :word)] [:td [word-component]]]
            [:tr [:td (i18n/translate @language :misses)] [:td (display-misses @round)]]
            (when (round/over? @round)
              (list [:tr [:td (i18n/translate @language :status)] [:td (display-result @round @language)]]
                    [:tr [:td {:colspan 2}
                          [:button
                           {:on-click #(re-frame/dispatch [:new-round])}
                           (i18n/translate @language :play-again)]]]))]]]]]])))
