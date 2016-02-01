(ns hangman.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [hangman.core-test]))

(doo-tests 'hangman.core-test)
