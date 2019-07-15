package com.anushka.androidtutz.contactmanager;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.widget.Toast;

import com.anushka.androidtutz.contactmanager.db.ContactsAppDatabase;
import com.anushka.androidtutz.contactmanager.db.entity.Contact;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class ContactRepository {

    private Application application;
    private ContactsAppDatabase contactsAppDatabase;

    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<List<Contact>> contactListLiveData = new MutableLiveData<>();

    private long rowIdOfTheItemInserted;

    public ContactRepository(Application application) {
        this.application = application;
        contactsAppDatabase = Room.databaseBuilder(application.getApplicationContext(), ContactsAppDatabase.class, "ContactDB").build();

        disposable.add(contactsAppDatabase.getContactDAO().getContacts()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Contact>>() {
                               @Override
                               public void accept(List<Contact> contacts) throws Exception {
                                   contactListLiveData.postValue(contacts);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {

                               }
                           }
                )
        );
    }

    public void createContact(final String name, final String email) {
        disposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                rowIdOfTheItemInserted = contactsAppDatabase.getContactDAO().addContact(new Contact(0, name, email));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(application.getApplicationContext(), " contact added successfully " + rowIdOfTheItemInserted, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(application.getApplicationContext(), " error occurred ", Toast.LENGTH_LONG).show();

                    }
                }));
    }

    public void updateContact(final Contact contact) {

        disposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

                contactsAppDatabase.getContactDAO().updateContact(contact);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(application.getApplicationContext(), " contact updated successfully ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(application.getApplicationContext(), " error occurred ", Toast.LENGTH_LONG).show();

                    }
                }));


    }

    public void deleteContact(final Contact contact) {

        disposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

                contactsAppDatabase.getContactDAO().deleteContact(contact);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(application.getApplicationContext(), " contact deleted successfully ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(application.getApplicationContext(), " error occurred ", Toast.LENGTH_LONG).show();

                    }
                }));
    }

    public void clear(){
        disposable.clear();
    }

    public MutableLiveData<List<Contact>> getContactListLiveData(){
        return contactListLiveData;
    }
}
