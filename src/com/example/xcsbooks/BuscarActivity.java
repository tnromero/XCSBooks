package com.example.xcsbooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Livro;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import control.BuscaControl;

public class BuscarActivity extends BaseActivity {
	private String termo;
	private EditText mEditBusca;
	private List<Livro> livros;
	private List searchList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar);
	
		termo = getIntent().getStringExtra(BaseActivity.KEY_BUSCA);
		mEditBusca = (EditText) findViewById(R.id.busca_txtBusca);
		mEditBusca.setText(termo);
		mEditBusca.clearFocus();
			
		ListView lv = (ListView) findViewById(R.id.busca_listaResultadosView);
		searchList = new ArrayList();
		performSearch(termo);
		
		String[] t = {"itemLista_thumbLivro","itemLista_tituloLivro",
					"itemLista_autorLivro", "itemLista_precoLivro"};
			
		int[] i = {R.id.itemLista_thumbLivro,
					R.id.itemLista_tituloLivro, R.id.itemLista_autorLivro, R.id.itemLista_precoLivro};
		
		//Adapter
	
		final SimpleAdapter adapter = new SimpleAdapter(this, (List <? extends Map<String, ?>>) searchList, R.layout.item_lista, t , i);
		
		lv.setAdapter(adapter);
	
		lv.setOnItemClickListener(new OnItemClickListener() {
		
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(BuscarActivity.this, DetalhesLivroActivity.class);
				startActivity(intent);
			}
		});
		
		mEditBusca.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				termo = v.getText().toString();
				performSearch(termo);
				adapter.notifyDataSetChanged();
				return true;
			}
		});
	}
	
	private void performSearch(String termo){
		//Obt�m livros da busca
		livros = BuscaControl.buscar(termo);
		searchList.clear();
		
		Map map = null;
		for(int i = 0; i < livros.size(); i++) {
			map = new HashMap();
			map.put("itemLista_thumbLivro", R.drawable.book_icon);
			map.put("itemLista_tituloLivro", livros.get(i).getTitulo());
			map.put("itemLista_autorLivro", livros.get(i).getAutor());
			//map.put("itemLista_precoLivro", String.valueOf(livros.get(i).getPreco()));
			map.put("itemLista_precoLivro", 10.5);
			searchList.add(map);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		return super.onOptionsItemSelected(item);
	}
	
}