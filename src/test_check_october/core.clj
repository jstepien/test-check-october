(ns test-check-october.core)

(defn steps-per-hour
  [steps seconds]
  (if (zero? seconds)
    (throw (java.lang.ArithmeticException.))
    (/ (* 3600.0 steps) seconds)))

(defn jan-reverse [coll]
  (reverse coll))
