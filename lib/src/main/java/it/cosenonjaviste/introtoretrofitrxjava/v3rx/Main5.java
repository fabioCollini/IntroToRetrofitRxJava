package it.cosenonjaviste.introtoretrofitrxjava.v3rx;

import rx.Observable;
import rx.Observer;

public class Main5 {
    public static void main(String[] args) {
        Observable.just(1, 2, 3).subscribe(
                System.out::println,
                Throwable::printStackTrace,
                () -> System.out.println("Completed")
        );

        Observable.just(1, 2, 3).subscribe(new Observer<Integer>() {
            @Override public void onCompleted() {
                System.out.println("Completed");
            }

            @Override public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override public void onNext(Integer integer) {
                System.out.println(integer);
            }
        });
    }
}
