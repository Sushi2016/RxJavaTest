package com.app.zhy.rxjavatest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, Thread.currentThread().getName());


        // safeSubscribe subscribe 的区别？？？
//        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onNext(3);
//                e.onNext(4);
//                e.onNext(5);
//                e.onComplete();
//            }
//        });
//
//        Observer<Integer> observer = new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.e(TAG,"onSubscribe");
//            }
//
//            @Override
//            public void onNext(Integer value) {
//                Log.e(TAG,"onNext  " + value);
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG,"onError");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e(TAG,"onComplete");
//            }
//        };
//        observable.safeSubscribe(observer);


        //  Disposable 为了不让Observer继续接收Observable发来的数据
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                Log.e(TAG,"e " + 1);
//                e.onNext(1);
//                Log.e(TAG,"e " + 2);
//                e.onNext(2);
//                Log.e(TAG,"e " + 3);
//                e.onNext(3);
//                Log.e(TAG,"e " + 4);
//                e.onNext(4);
//                Log.e(TAG,"onComplete ");
//                e.onComplete();
//                Log.e(TAG,"e " + 5);
//                e.onNext(5);
//            }
//        }).subscribe(new Observer<Integer>() {
//
//            Disposable disposable;
//            int i=0;
//
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.e(TAG,"onSubscribe");
//                disposable = d;
//            }
//
//            @Override
//            public void onNext(Integer value) {
//                Log.e(TAG,"onNext  " + value);
//                i++;
//                if(i == 2){
//                    Log.e(TAG, "dispose");
//                    disposable.dispose();
//                    Log.e(TAG, "isDisposed " + disposable.isDisposed());
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG,"onError");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e(TAG,"onComplete");
//            }
//        });


        // Consumer  只需要onNext的时候用
        // AndroidSchedulers.mainThread()  主线程
        // Schedulers.io()  子线程（用于io流的操作   网络请求或者文件操作）  有线程池维护
        // Schedulers.newThread()  新开子线程  没有线程池维护
        // subscribeOn  改变 Observable的线程   只有指定的第一次有效
        // observeOn 改变 Observer的线程        可以指定多个线程
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                Log.e(TAG, "Observable  " + Thread.currentThread().getName());
//                e.onNext(1);
//                e.onNext(2);
//                e.onNext(3);
//                e.onNext(4);
//                e.onNext(5);
//                e.onComplete();
//            }
//        }).subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(Schedulers.io()).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.e(TAG, "Consumer  " + Thread.currentThread().getName());
//                Log.e(TAG, "accept  " + integer);
//            }
//        });

        //map  用于任意类型转换  Object  或者是集合 都可以
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onNext(3);
//                e.onNext(4);
//                e.onNext(5);
//                e.onComplete();
//            }
//        }).map(new Function<Integer, Double>() {
//            @Override
//            public Double apply(Integer integer) throws Exception {
//                return Double.valueOf(integer);
//            }
//        }).subscribe(new Consumer<Double>() {
//            @Override
//            public void accept(Double aDouble) throws Exception {
//                Log.e(TAG, "accept  " + aDouble);
//            }
//        });

        //  concatMap  有序的   flatMap有可能是无序的  这两个跟和map一样都是转换  但是有区别的是 concatMap和flatMap 可以将observable转换为多个observables
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onNext(3);
//                e.onNext(4);
//                e.onNext(5);
//            }
//        }).concatMap(new Function<Integer, ObservableSource<String>>() {
//            @Override
//            public ObservableSource<String> apply(Integer integer) throws Exception {
//                final List<String> list = new ArrayList<>();
//                for (int i = 0; i < 3; i++) {
//                    list.add("flatMap " + integer);
//                }
//                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.e(TAG, s);
//            }
//        });


        //java.lang.InterruptedException  为什么在两个observable 事件数量不一样的时候会报java.lang.InterruptedException????
        //在主线程就不会报错  但是在io线程中就会报java.lang.InterruptedException
//        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                Log.e(TAG, "emit 1");
//                emitter.onNext(1);
//                Thread.sleep(1000);
//
//                Log.e(TAG, "emit 2");
//                emitter.onNext(2);
//                Thread.sleep(1000);
//
//                Log.e(TAG, "emit 3");
//                emitter.onNext(3);
//                Thread.sleep(1000);
//
//                Log.e(TAG, "emit 4");
//                emitter.onNext(4);
//                Thread.sleep(1000);
//
//                Log.e(TAG, "emit complete1");
//                emitter.onComplete();
//            }
//        }).subscribeOn(Schedulers.io());
//
//        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                Log.e(TAG, "emit A");
//                emitter.onNext("A");
//                Thread.sleep(1000);
//
//                Log.e(TAG, "emit B");
//                emitter.onNext("B");
//                Thread.sleep(1000);
//
//                Log.e(TAG, "emit C");
//                emitter.onNext("C");
//                Thread.sleep(1000);
//
////                Log.e(TAG, "emit D");
////                emitter.onNext("D");
////                Thread.sleep(1000);
//
//                Log.e(TAG, "emit complete2");
//                emitter.onComplete();
//            }
//        }).subscribeOn(Schedulers.io());
//
//        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
//            @Override
//            public String apply(Integer integer, String s) throws Exception {
//                return integer + s;
//            }
//        }).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.e(TAG, "onSubscribe");
//            }
//
//            @Override
//            public void onNext(String value) {
//                Log.e(TAG, "onNext: " + value);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, "onError");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e(TAG, "onComplete");
//            }
//        });

//        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                for (int i = 0;; i++) {   //无限循环发事件
//                    emitter.onNext(i);
//                }
//            }
//        }).subscribeOn(Schedulers.io());
//
//        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext("A");
//            }
//        }).subscribeOn(Schedulers.io());
//
//        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
//            @Override
//            public String apply(Integer integer, String s) throws Exception {
//                return integer + s;
//            }
//        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.d(TAG, s);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                Log.w(TAG, throwable);
//            }
//        });

    }
}
