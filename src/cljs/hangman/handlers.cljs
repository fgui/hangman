(ns hangman.handlers
    (:require [re-frame.core :as re-frame]
              [hangman.db :as db]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/hangman))

(re-frame/register-handler
 :try-letter
 (fn  [db [_ letter]]
   (update db :display-word (constantly letter))
   ))
