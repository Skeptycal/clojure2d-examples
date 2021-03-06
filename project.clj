(defproject clojure2d-examples "1.1.0"
  :description "Examples for Clojure2d library"
  :url "https://github.com/Clojure2D/clojure2d-examples"
  :license {:name "The Unlicense"
            :url "http://unlicense.org"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [net.littleredcomputer/sicmutils "0.10.0"]
                 [generateme/fastmath "1.2.0"]
                 [clojure2d "1.1.0"]]
  :repl-options {:timeout 120000}
  :target-path "target/%s"
  :jvm-opts ["-Xmx4096M"]
  :profiles {:dev {:plugins [[refactor-nrepl "2.4.0"]
                             [cider/cider-nrepl "0.19.0-SNAPSHOT"]]}})
