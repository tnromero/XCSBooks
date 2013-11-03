package com.example.xcsbooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import com.example.xcsbooks.control.GetBookCover;
import com.example.xcsbooks.control.JSONParser;
import com.examples.xcsbooks.model.LivroNovo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class CarrinhoActivity extends BaseActivity {
	private SharedPreferences prefs;
	private ListView mLv; 
	private List<LivroNovo> livros;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carrinho);
		
		//mBtnComprar = (Button) findViewById(R.id.carrinho_btnComprar);
		
		mLv = (ListView) findViewById(R.id.carrinho_listaItensView);
		
		buildShoppingCart();
		View v = getLayoutInflater().inflate(R.layout.carrinho_footer, null);
		Button footerComprar = (Button) v.findViewById(R.id.carrinho_btnComprar);
		mLv.addFooterView(v);
		
		
		footerComprar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CarrinhoActivity.this, ComprarActivity.class);
				startActivity(intent);
			}
		});
		
	}
	
	private void buildShoppingCart(){
		prefs = getSharedPreferences("CARRINHO", MODE_PRIVATE);
		String strlivros = prefs.getString("LIVROS", JSONParser.DEFAULT_LIVROS);
		livros = JSONParser.LivroFromJSON(strlivros);
		
		List list = new ArrayList();
		Map map = null;
		
		for(int i = 0; i < livros.size(); i++) {
			map = new HashMap();
			map.put("itemCarrinho_thumbLivro", GetBookCover.getCover(livros.get(i).getIsbn()));
			map.put("itemCarrinho_tituloLivro", livros.get(i).getTitulo().toString());
			map.put("itemCarrinho_autorLivro", livros.get(i).getAutor().toString());
			map.put("itemCarrinho_editoraLivro", livros.get(i).getEditora());
			map.put("itemCarrinho_quantidade", 1);
			map.put("itemCarrinho_precoLivro", "R$ " + livros.get(i).getPreco());
			list.add(map);
		}
		
		//Adapter
		final CarrinhoListAdapter adapter = new CarrinhoListAdapter(this, (List<HashMap<String, Object>>) list, 
			R.layout.item_carrinho, 
			new String[] {
				"itemCarrinho_thumbLivro",
				"itemCarrinho_tituloLivro",
				"itemCarrinho_autorLivro",
				"itemCarrinho_editoraLivro",
				"itemCarrinho_quantidade",
				"itemCarrinho_precoLivro"},
			new int[] {
				R.id.itemCarrinho_thumbLivro,
				R.id.itemCarrinho_txtTituloLivro,
				R.id.itemCarrinho_txtAutorLivro,
				R.id.itemCarrinho_txtEditoraLivro,
				R.id.itemCarrinho_txtQuantidadeLivro,
				R.id.itemCarrinho_txtPrecoLivro});
		
		mLv.setAdapter(adapter);
	}
	
	private void cleanShoppingCart(){
		mLv.setAdapter(null);
	}
	
	@Override
	protected void onResume(){
		super.onRestart();
		buildShoppingCart();
	}
	
	@Override
	protected void onPause(){
		super.onPause();
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
