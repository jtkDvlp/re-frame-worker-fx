(defproject re-frame-worker-fx "1.0.0-alpha"
  :description "A re-frame effects handler for performing async tasks via cljs-workers"
  :url "https://github.com/jtkDvlp/re-frame-worker-fx"
  :license {:name "MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]

                 [cljs-workers "1.0.1-alpha"]
                 [re-frame "0.9.1"]]

  :min-lein-version "2.5.3"

  :source-paths ["src"]
  :test-paths ["test"]

  :clean-targets ^{:protect false} ["resources/public/js" "target"]

  :plugins [[lein-cljsbuild "1.1.4" :exclusions [[org.clojure/clojure]]]
            [lein-figwheel "0.5.0-1"]]

  :profiles {:dev {:dependencies [[figwheel-sidecar "0.5.8"]
                                  [com.cemerick/piggieback "0.2.1"]]}}

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

  :cljsbuild
  {:builds
   [{:id "test"
     :source-paths ["src" "test"]
     :figwheel true
     :compiler {:main re-frame-worker-fx.test
                :asset-path "js/test/out"
                :output-to "resources/public/js/test/test.js"
                :output-dir "resources/public/js/test/out"}}
    {:id "worker"
     :source-paths ["src" "test"]
     :compiler {:main re-frame-worker-fx.test
                :asset-path "js/worker/out"
                :output-to "resources/public/js/worker/worker.js"
                :output-dir "resources/public/js/worker/out"
                :optimizations :simple}}]})
