(ns hangman.handlers
    (:require [re-frame.core :as re-frame]
              [hangman.db :as db]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/state))

(re-frame/register-handler
 :try-letter
 (fn  [db [_ letter]]
   (update-in db [:word :tried-letters] conj letter)
   ))
