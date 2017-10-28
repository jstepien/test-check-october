(ns test-check-october.core
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as st]))

(defn steps-per-hour
  [steps seconds]
  (if (zero? seconds)
    (throw (java.lang.ArithmeticException.))
    (/ (* 3600.0 steps) seconds)))


(defn jan-reverse [coll]
  (reverse coll))

(defn first-is-last [{ret :ret {coll :coll} :args}]
  (= (first ret)
     (last coll)))

(defn retain-count [{ret :ret {coll :coll} :args}]
  (= (count ret)
     (count coll)))

(defn retain-elements [{ret :ret {coll :coll} :args}]
  (= (set ret)
     (set coll)))

(defn involution [{ret :ret {coll :coll} :args}]
  (= (jan-reverse ret)
     coll))

(s/fdef jan-reverse
        :args (s/cat :coll sequential?)
        :ret sequential?
        :fn (s/and first-is-last
                   retain-count
                   retain-elements
                   involution))

(st/instrument)
#_(jan-reverse 5)

