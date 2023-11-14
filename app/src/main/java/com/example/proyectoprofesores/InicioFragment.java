package com.example.proyectoprofesores;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment  implements  Response.ErrorListener {

   // ArrayList<String> listaCursos;

    ArrayList<CursoInicio> listaCursos = new ArrayList<>();

    ArrayList<Justificacion> listaJustificaciones = new ArrayList<>();
    RecyclerView recyclerCurso;
    RecyclerView recyclerJustificacion;
    TextView usuario_bienvenida;
    String idUsuario;
    String idDocente;
    String nombre;
    String apellido;
    String correo;

    JsonArrayRequest jsonArrayRequestCurso;
    JsonArrayRequest jsonArrayRequestJusti;
    private ActivityResultLauncher<Intent> scanActivityResultLauncher;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        // ... (código existente)
        Bundle args = getArguments();
        idUsuario = args.getString("idUsuario", "");
        idDocente =args.getString("idDocente", "");
        nombre = args.getString("nombre", "");
        apellido = args.getString("apellido", "");
        correo = args.getString("correo", "");
        String[] partes = nombre.split(" ");
        String primerNombre = partes[0];
        usuario_bienvenida = view.findViewById(R.id.usuario_bienvenida);
        usuario_bienvenida.setText("Bienvenido, "+primerNombre+"!");
        ImageView rectangulo_barra = view.findViewById(R.id.vermas);
        rectangulo_barra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgendaFragment fragment = new AgendaFragment();

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        TextView moreoption = view.findViewById(R.id.moreCursos);
        moreoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoursesFragment fragment = new CoursesFragment();

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        TextView moreJust = view.findViewById(R.id.moreJust);

        moreJust.setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View v) {
                JustifyFragment fragment = new JustifyFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        // ... (resto del código)
        //instancia del recyclerCurso
        recyclerCurso = view.findViewById(R.id.recyclerCourseId);
        recyclerCurso.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerCurso.setHasFixedSize(true);
        cargarWebServiceCurso();

        //instancia del recycleJusti
        recyclerJustificacion = view.findViewById(R.id.recyclerJustiId);
        recyclerJustificacion.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerJustificacion.setHasFixedSize(true);
        cargarWebServiceJustificacion();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView scanPlace = view.findViewById(R.id.scan_place);
        ImageView notiPlace = view.findViewById(R.id.bell_place);
        ImageView userPlace = view.findViewById(R.id.user_place);
        scanActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            IntentResult scanResult = IntentIntegrator.parseActivityResult(
                                    IntentIntegrator.REQUEST_CODE,
                                    Activity.RESULT_OK,
                                    data
                            );

                            if (scanResult != null) {
                                String contents = scanResult.getContents();
                                if (contents == null) {
                                    Toast.makeText(requireContext(), "Escaneo cancelado", Toast.LENGTH_LONG).show();
                                } else {
                                    verificarAsistencia(contents);
                                }
                            }
                        }
                    }
                }
        );

        scanPlace.setOnClickListener(v -> iniciarEscaneo());
        notiPlace.setOnClickListener(v -> {
            Intent intent = new Intent( getContext(), NotificacionesActivity.class);
            intent.putExtra("idUsuario", idUsuario);
            intent.putExtra("idDocente", idDocente);
            startActivity(intent);
        });
        userPlace.setOnClickListener(v -> {

            Intent intent = new Intent( getContext(), PerfilActivity.class);
            intent.putExtra("idUsuario", idUsuario);
            intent.putExtra("idDocente", idDocente);
            intent.putExtra("nombre", nombre);
            intent.putExtra("apellido", apellido);
            intent.putExtra("correo", correo);
            startActivity(intent);
        });

    }


    private void cargarWebServiceCurso() {
        String ip = "https://proyectoprofesores.000webhostapp.com";
        String idDocenteURL ="?id_docente=" + idDocente;
        String url = ip + "/obtenerCursosInicio.php" + idDocenteURL; //cambiar

        jsonArrayRequestCurso= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i=0; i<response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);

                        CursoInicio cursosInicio =  new CursoInicio.Builder()
                                .withIdCurso(jsonObject.optString("id_cursos"))
                                .withCursos(jsonObject.optString("curso"))
                                .build();

                        listaCursos.add(cursosInicio);
                    }
                    AdapterCurso adapter =  new AdapterCurso(listaCursos, getContext());
                    recyclerCurso.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error carga cursos" + " " + response, Toast.LENGTH_LONG).show();
                }
            }
        }, this);

        //jsonArrayRequestCurso= new JsonArrayRequest(Request.Method.GET, url, null, this, this );
        //request.add(jsonArrayRequest);
        jsonArrayRequestCurso.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VoleySingleton.getIntanciaV(getContext()).addToRequestQueue(jsonArrayRequestCurso);
    }

    private void cargarWebServiceJustificacion(){
        String ip = "https://proyectoprofesores.000webhostapp.com";
        String idDocenteURL ="?id_docente=" + idDocente;
        String url = ip + "/obtenerJustificacionesInicio.php" + idDocenteURL;

        jsonArrayRequestJusti= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i=0; i<response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);

                        Justificacion justificacion = new Justificacion(Integer.valueOf(jsonObject.optString("idJusti")), jsonObject.optString("nomAlumno"), jsonObject.optString("aula"), java.sql.Date.valueOf(jsonObject.optString("fecha")));

                        listaJustificaciones.add(justificacion);
                    }
                    AdapterJustificacion adapterj = new AdapterJustificacion(listaJustificaciones);
                    recyclerJustificacion.setAdapter(adapterj);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error carga justificaciones" + " " + response, Toast.LENGTH_LONG).show();
                }
            }
        }, this);

        jsonArrayRequestJusti.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VoleySingleton.getIntanciaV(getContext()).addToRequestQueue(jsonArrayRequestJusti);

    }



    private void iniciarEscaneo() {
        IntentIntegrator integrator = new IntentIntegrator(requireActivity());
        integrator.setOrientationLocked(true);
        integrator.setPrompt("Escanea el código QR");
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);

        Intent intent = integrator.createScanIntent();
        scanActivityResultLauncher.launch(intent);
    }



    private void verificarAsistencia(String codigoQR) {
        String horaActual = obtenerHoraActual();

        if (codigoQR.equals("3A") && isHoraEnRango(horaActual)) {
            mostrarSnackbar("Bienvenido al " + codigoQR + ", llegó a tiempo");
        } else {
            mostrarSnackbar("Se encuentra en el " + codigoQR + ", está fuera de horario");
        }
    }

    private String obtenerHoraActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private boolean isHoraEnRango(String hora) {
        try {
            Date horaDate = new SimpleDateFormat("HH:mm").parse(hora);
            Date horaInicioDate = new SimpleDateFormat("HH:mm").parse("23:48");
            Date horaFinDate = new SimpleDateFormat("HH:mm").parse("23:59");

            assert horaDate != null;
            return horaDate.after(horaInicioDate) && horaDate.before(horaFinDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void mostrarSnackbar(String mensaje) {
        Snackbar snackbar = Snackbar.make(requireView(), mensaje, Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();

        // Personalizar el color de fondo
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setBackgroundColor(Color.parseColor("#222222"));

        // Personalizar el tamaño de la letra
        TextView textView = snackbarLayout.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setTypeface(null, Typeface.BOLD);
        // Quitar el botón de acción si lo hay
        snackbar.setAction(null, null);

        // Mostrar el Snackbar personalizado
        snackbar.show();
    }
    private void mostrarResultadoEnToast(String resultado) {
        Toast.makeText(requireContext(), resultado, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se puede conectar cursosss" + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR:", error.toString());

    }

}