(ns clojure-in-production.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [clojure-in-production.core-test]))

(doo-tests 'clojure-in-production.core-test)

