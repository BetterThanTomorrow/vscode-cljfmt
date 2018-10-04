(ns calva.fmt.formatter-test
  (:require [cljs.test :include-macros true :refer [deftest is]]
            [calva.fmt.formatter :as sut]))

;; TODO: Fix this bug (gazonk should be indented twice)
#_(deftest format-text
    (is (= "  (foo\n   bar\n   baz)\n  gazonk"
           (:text (sut/format-text {:text "  (foo   \nbar\n      baz)\ngazonk"})))))

(deftest normalize-indents
  (is (= "(foo)\n  (defn bar\n    [x]\n    baz)"
         (:text (sut/normalize-indents {:all-text "  (foo)\n(defn bar\n[x]\nbaz)"
                                        :range [2 26]
                                        :text "(foo)\n(defn bar\n  [x]\n  baz)"})))))

(deftest format-text-at-range
  (is (= "(foo)\n  (defn bar\n    [x]\n    baz)"
         (:text (sut/format-text-at-range {:all-text "  (foo)\n(defn bar\n[x]\nbaz)" :range [2 26]})))))

(deftest format-text-at-idx
  (is (= "(defn bar\n    [x]\n    baz)"
         (:text (sut/format-text-at-idx {:all-text "  (foo)\n  (defn bar\n[x]\nbaz)" :idx 11}))))
  (is (= [10 28]
         (:range (sut/format-text-at-idx {:all-text "  (foo)\n  (defn bar\n[x]\nbaz)" :idx 11})))))

(deftest tail-text-wo-whitespace
  (is (= "obar"
         (sut/tail-text-wo-whitespace " fo oba r " 3))))