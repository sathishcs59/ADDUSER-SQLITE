package mine.manik.com.dbsample;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mine.manik.com.dbsample.Adapter.EmpAdapter;
import mine.manik.com.dbsample.DB.DBHandler;
import mine.manik.com.dbsample.Pojo.Emp;

public class MainActivity extends AppCompatActivity {

    int reqForRefresh = 191;

    RecyclerView mRecyclerView;
    EmpAdapter mEmpAdapter;
    ArrayList<Emp> mUserList;

    FloatingActionButton fabCreateNew;
    TextView tvEmpty;
    DBHandler mDBHandler;

    String[] optionConnect = {"Edit", "Delete"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    void initViews() {

        mDBHandler = new DBHandler(this);
        tvEmpty = (TextView) findViewById(R.id.emp_empty);
        fabCreateNew = (FloatingActionButton) findViewById(R.id.emp_add);
        mRecyclerView = (RecyclerView) findViewById(R.id.emp_recyclerview);

        fetchEmps();

        fabCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewEmpActivity.class);
                startActivityForResult(intent, reqForRefresh);
            }
        });

    }

    void fetchEmps() {

        mUserList=new ArrayList<>();
        Cursor cursor = mDBHandler.getEmpDetails();

        if (cursor != null) {

            if (cursor.moveToFirst()) {
                do {

                    Emp item = new Emp();
                    item.setId(cursor.getInt(0));
                    item.setName(cursor.getString(1));
                    item.setMobile(cursor.getString(2));
                    item.setDep(cursor.getString(3));
                    mUserList.add(item);

                } while (cursor.moveToNext());
            }

        }

        if (mUserList.size() > 0) {

            tvEmpty.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

            if (mEmpAdapter == null) {
                mEmpAdapter = new EmpAdapter(this, mUserList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
                mRecyclerView.setAdapter(mEmpAdapter);
                mEmpAdapter.notifyDataSetChanged();

                mEmpAdapter.setOnClickListener(new EmpAdapter.OnClickListener() {
                    @Override
                    public void onLayoutClick(int position) {
                        showOptionDialog(position);
                    }
                });
            } else {
                mEmpAdapter.setUserList(mUserList);
                mEmpAdapter.notifyDataSetChanged();

                if (mUserList.size() > 0) {
                    tvEmpty.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    tvEmpty.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
            }

        } else {
            tvEmpty.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }

    }

    void showOptionDialog(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Action")
                .setItems(optionConnect, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        if (which == 0) {
                            Intent intent = new Intent(MainActivity.this, NewEmpActivity.class);
                            intent.putExtra("empInfo",mUserList.get(position));
                            startActivityForResult(intent, reqForRefresh);
                        } else if (which == 1) {
                            mDBHandler.removeProfile(mUserList.get(position).getMobile());
                            mUserList.remove(position);
                            mEmpAdapter.setUserList(mUserList);
                            mEmpAdapter.notifyDataSetChanged();

                            if (mUserList.size() > 0) {
                                tvEmpty.setVisibility(View.GONE);
                                mRecyclerView.setVisibility(View.VISIBLE);
                            } else {
                                tvEmpty.setVisibility(View.VISIBLE);
                                mRecyclerView.setVisibility(View.GONE);
                            }
                        }
                    }
                });

        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == reqForRefresh) {

                if (mEmpAdapter == null) {
                    fetchEmps();
                } else {
                    fetchEmps();
                }

            }
        }
    }
}
