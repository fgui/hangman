(ns hangman.handlers
  (:require [re-frame.core :as re-frame]
            [hangman.db :as db]
            [hangman.game :as game]
            [hangman.dictionary :as dictionary]))

(defn init-word [db word]
  (assoc db :round (game/guess-this-word word)))

(defn new-random-word [db]
  (init-word db (dictionary/random-word)))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   (new-random-word db/state)))

(re-frame/register-handler
 :try-letter
 (fn  [db [_ letter]]
   (if-not (game/game-over? (:round db))
     (update-in db [:round :tried-letters] conj letter)
     db)))

(defn update-accumulated-score [db]
  (update db :accumulated-score + (game/score db)))

(re-frame/register-handler
 :new-round
 (fn [db [_ _]]
   (-> db
       update-accumulated-score
       new-random-word)))
