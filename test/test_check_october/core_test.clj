(ns test-check-october.core-test
  (:require [clojure.test :refer :all]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [test-check-october.core :refer :all]
            [clojure.spec.test.alpha :as st]))

(deftest t-steps-per-hour
  (are [steps seconds]
       (= (double steps)
          (* seconds
             (steps-per-hour steps seconds)
             1/3600))
         1 3600
         1 7200
         0 7200))

(defspec prop-steps-per-hour
  (prop/for-all
    [steps gen/int
     seconds (gen/such-that (fn [x] (not= 0 x))
                            gen/int)]
    (comment
      (= (double steps)
         (* seconds
            (steps-per-hour steps seconds)
            1/3600)))
    true))

(deftest t-steps-per-hour-edge-case
  (is (thrown? java.lang.NullPointerException
               (steps-per-hour 1 nil)))
  (is (thrown? java.lang.ArithmeticException
               (steps-per-hour 1 0))))

(defspec t-reverse-count
  (prop/for-all
    [coll (gen/vector gen/int)]
    (= (count coll)
       (count (jan-reverse coll)))))

(defspec t-reverse-first-last
  (prop/for-all
    [coll (gen/vector gen/int)]
    (= (first coll)
       (last (jan-reverse coll)))))

(defspec t-reverse-members
  (prop/for-all
    [coll (gen/vector gen/int)]
    (= (set coll)
       (set (jan-reverse coll)))))

(defspec t-reverse-reverse
  (prop/for-all
    [coll (gen/vector gen/int)]
    (= coll
       (jan-reverse (jan-reverse coll)))))


(st/check `jan-reverse {:clojure.spec.test.check/opts {:num-tests 10}})


