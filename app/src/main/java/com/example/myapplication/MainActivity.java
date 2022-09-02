package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import java.math.BigInteger;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    A a;
    //TextView retrieve = (TextView) findViewById(R.id.retrieve);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView retrieve = (TextView) findViewById(R.id.retrieve);

        Web3j web3 = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/c5c97d9515494e139392811400049d57"));
        Credentials credentials = Credentials.create("b8b86ab9eb4f4730ffbf540b38c4ecdc27974fe5c7f1179c02c4e242f9046d83");
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        a = A.load("0xdDbcd1825E4D346C80FE7A781179C8c104F48d0b", web3, credentials, contractGasProvider);

        a.retrieve().flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer<BigInteger>() {
            @Override
            public void accept(BigInteger bigInteger) throws Exception {
                Log.i("vac", "accept: " + bigInteger);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        retrieve.setText(String.valueOf(bigInteger));
                    }
                });
            }
        });
    }

    public void clk(View view){
        EditText promptText = (EditText) findViewById(R.id.promptText);
        TextView retrieve = (TextView) findViewById(R.id.retrieve);
        String inputText = promptText.getText().toString();
        retrieve.setText(inputText);
        a.store(new BigInteger(inputText)).flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer<TransactionReceipt>() {
            @Override
            public void accept(TransactionReceipt transactionReceipt) throws Exception {
                Log.i("vac", "accept: ");
            }
        });
    }
}