;; https://generateme.wordpress.com/2016/05/04/curvature-from-noise/

(ns ex09-curvature
  (:require [clojure2d.core :refer :all]
            [fastmath.core :as m]
            [fastmath.random :as r]
            [fastmath.vector :as v]
            [fastmath.fields :refer :all]
            [clojure.pprint :refer [pprint]]) 
  (:import [fastmath.vector Vec2 Vec3]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)
(m/use-primitive-operators)

(def ^:const ^long w 800)
(def ^:const ^long h 800)
(def ^:const ^long border 200)

(def ^:const ^double point-step 1.0)
(def ^:const ^double point-size 0.7)
(def ^:const ^double coord-scale 3.0)
(def ^:const ^double angle-scale 7.0)

(defn make-particle
  ""
  []
  (Vec3. (r/drand border (- w border)) (r/drand border (- h border)) (r/drand m/TWO_PI)))

(defn move-particle
  ""
  [^Vec2 vshift fun noise canvas ^Vec3 in]
  (let [nx (+ (.x in) (* point-step (m/qcos (.z in))))
        ny (+ (.y in) (* point-step (m/qsin (.z in))))
        xx (m/norm nx 0 w -1 1)
        yy (m/norm ny 0 h -1 1)
        v (fun (v/mult (Vec2. xx yy) coord-scale))
        ^Vec2 vv (v/add v vshift)
        angle (+ (.z in) (* angle-scale ^double (m/norm (noise (.x vv) (.y vv)) 0 1 -1 1)))]

    (point canvas nx ny)

    (Vec3. nx ny angle)))

(defn example-09
  []
  (binding [*skip-random-fields* true]
    (let [cnvs (canvas w h)
          window (show-window cnvs "curvature" 15 nil)
          noise (r/random-noise-fn)
          field-config (random-configuration)
          field (combine field-config)
          vshift (Vec2. (r/drand -3 3) (r/drand -3 3))
          mv-fun (partial move-particle vshift field noise)
          particles (repeatedly 5000 make-particle)
          looper (fn [canvas] (loop [xs particles]
                                (if (window-active? window)
                                  (recur (mapv (partial mv-fun canvas) xs))
                                  canvas)))]
      
      (defmethod key-pressed ["curvature" \space] [_ _]
        (save cnvs (next-filename "results/ex09/" ".jpg")))

      (pprint field-config)

      (with-canvas-> cnvs
        (set-background 240 240 240)
        (set-color 20 20 20 20)
        (set-stroke point-size)
        (looper)))))

(example-09)

