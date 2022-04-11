package com.ahmed.virtualcube.schoolproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.widget.LinearLayout;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.TextView;
import com.google.android.material.button.*;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import com.catalinjurjiu.animcubeandroid.*;
import com.rm.rmswitch.*;
import com.shashank.sony.fancytoastlib.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;

public class MainActivity extends AppCompatActivity {
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private FloatingActionButton _fab;
	private DrawerLayout _drawer;
	private String Moves = "";
	private HashMap<String, Object> UpdatifyMap = new HashMap<>();
	private double backface = 0;
	private  int bface;
	
	private ArrayList<String> moves = new ArrayList<>();
	
	private AnimCube animcube;
	private LinearLayout _drawer_linear1;
	private TextView _drawer_textview1;
	private MaterialButton _drawer_resetbut;
	private MaterialButton _drawer_scramblebut;
	private MaterialButton _drawer_materialbutton1;
	private MaterialButton _drawer_optionsbut;
	private LinearLayout _drawer_linear2;
	private MaterialButton _drawer_exitbut;
	
	private AlertDialog.Builder d;
	private Intent i = new Intent();
	private SharedPreferences setting;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_app_bar = findViewById(R.id._app_bar);
		_coordinator = findViewById(R.id._coordinator);
		_toolbar = findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		_fab = findViewById(R.id._fab);
		
		_drawer = findViewById(R.id._drawer);
		ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(MainActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
		_drawer.addDrawerListener(_toggle);
		_toggle.syncState();
		
		LinearLayout _nav_view = findViewById(R.id._nav_view);
		
		animcube = findViewById(R.id.animcube);
		_drawer_linear1 = _nav_view.findViewById(R.id.linear1);
		_drawer_textview1 = _nav_view.findViewById(R.id.textview1);
		_drawer_resetbut = _nav_view.findViewById(R.id.resetbut);
		_drawer_scramblebut = _nav_view.findViewById(R.id.scramblebut);
		_drawer_materialbutton1 = _nav_view.findViewById(R.id.materialbutton1);
		_drawer_optionsbut = _nav_view.findViewById(R.id.optionsbut);
		_drawer_linear2 = _nav_view.findViewById(R.id.linear2);
		_drawer_exitbut = _nav_view.findViewById(R.id.exitbut);
		d = new AlertDialog.Builder(this);
		setting = getSharedPreferences("setting", Activity.MODE_PRIVATE);
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_drawer.openDrawer(GravityCompat.START);
			}
		});
		
		_drawer_resetbut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				d.setTitle("Confirmation");
				d.setMessage("Are you sure that you want to reset the cube?\nall changes will be lost");
				d.setPositiveButton("yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						i.setAction(Intent.ACTION_VIEW);
						i.setClass(getApplicationContext(), ReLaunchActivity.class);
						startActivity(i);
						SketchwareUtil.CustomToastWithIcon(getApplicationContext(), "Success! ", 0xFFFFFFFF, 15, 0xFF43A047, 20, SketchwareUtil.BOTTOM, R.drawable.ic_check_black);
						finish();
					}
				});
				d.setNeutralButton("no", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				d.create().show();
			}
		});
		
		_drawer_scramblebut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				AnimCube animCube = findViewById(R.id.animcube); 
				if (setting.getString("movesn", "").equals("random")) {
					for(int _repeat13 = 0; _repeat13 < (int)(SketchwareUtil.getRandom((int)(20), (int)(50))); _repeat13++) {
						Moves = Moves.concat(moves.get((int)(SketchwareUtil.getRandom((int)(0), (int)(26)))).concat(" "));
					}
				}
				else {
					for(int _repeat29 = 0; _repeat29 < (int)(Double.parseDouble(setting.getString("movesn", ""))); _repeat29++) {
						Moves = Moves.concat(moves.get((int)(SketchwareUtil.getRandom((int)(0), (int)(26)))).concat(" "));
					}
				}
				Moves = Moves.trim();
				animCube.setMoveSequence(Moves);
				animCube.animateMoveSequence();
				_drawer.closeDrawer(GravityCompat.START);
			}
		});
		
		_drawer_materialbutton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				AnimCube animCube = findViewById(R.id.animcube); 
				Moves = moves.get((int)(SketchwareUtil.getRandom((int)(0), (int)(26))));
				animCube.setMoveSequence(Moves);
				animCube.animateMoveSequence();
			}
		});
		
		_drawer_optionsbut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setClass(getApplicationContext(), SettingsActivity.class);
				startActivity(i);
			}
		});
		
		_drawer_exitbut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				d.setTitle("Confirmation");
				d.setMessage("Are you sure you want to exit?\nnone of your progress will not be saved");
				d.setPositiveButton("yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						finishAffinity();
					}
				});
				d.setNeutralButton("no", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				d.create().show();
			}
		});
	}
	
	private void initializeLogic() {
		if (!setting.contains("backfacesdistance")) {
			setting.edit().putString("backfacesdistance", "0").commit();
		}
		if (!setting.contains("scale")) {
			setting.edit().putString("scale", "0").commit();
		}
		if (!setting.contains("movesn")) {
			setting.edit().putString("movesn", "0").commit();
		}
		AnimCube animCube = findViewById(R.id.animcube);
		animCube.setEditable(true);
		moves.add("F");
		moves.add("U");
		moves.add("B");
		moves.add("D");
		moves.add("L");
		moves.add("R");
		moves.add("M");
		moves.add("E");
		moves.add("S");
		moves.add("F'");
		moves.add("U'");
		moves.add("B'");
		moves.add("D'");
		moves.add("L'");
		moves.add("R'");
		moves.add("M'");
		moves.add("S'");
		moves.add("E'");
		moves.add("F2");
		moves.add("U2");
		moves.add("B2");
		moves.add("D2");
		moves.add("L2");
		moves.add("R2");
		moves.add("M2");
		moves.add("E2");
		moves.add("S2");
		if (setting.getString("Backfaces", "").equals("true")) {
			animCube.setBackFacesDistance(Integer.parseInt(setting.getString("backfacesdistance", "")));
		}
	}
	
	@Override
	public void onBackPressed() {
		if (_drawer.isDrawerOpen(GravityCompat.START)) {
			_drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}