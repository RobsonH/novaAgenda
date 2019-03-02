package br.edu.ifro.vilhena.novaagendadecontatos;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.edu.ifro.vilhena.novaagendadecontatos.model.Contato;

public class AdapterPersonalizado extends BaseAdapter {

    private final List<Contato> listaPersonalizada;
    private final Activity activity;

    public AdapterPersonalizado(List<Contato> listaPersonalizada, Activity activity) {
        this.listaPersonalizada = listaPersonalizada;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listaPersonalizada.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPersonalizada.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaPersonalizada.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.lista_personalizada, parent, false);

        ImageView imageView = view.findViewById(R.id.lista_personalizada_foto);
        TextView textView = view.findViewById(R.id.lista_personalizada_texto);

        Contato contato = listaPersonalizada.get(position);

        textView.setText(contato.getNome());



        if (contato.getCaminhoFoto() != null){
            Bitmap bitmap = BitmapFactory.decodeFile(contato.getCaminhoFoto());
            Bitmap bitmapReduzido =  Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            imageView.setImageBitmap(bitmapReduzido);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setTag(contato.getCaminhoFoto());
        }

        return view;
    }
}
