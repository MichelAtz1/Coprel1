package br.desenvolvedor.michelatz.aplicativohcc;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class CadastrarConsumidores extends AppCompatActivity {

    private RadioGroup grupLado;
    private RadioButton radioDireita, radioEsquerda;
    private Spinner spnD10, spnQ10, spnW01, spnS03, spnQ25, spnW02, spnAF, spnQ35, spnW031, spnW032, spnW033;
    private Spinner spnLado, spnFaseamento, spnRamal, spnLocalizacao, spnMedicao, spnTipoMedidor, spnNumeroDigitos, spnMarca, spnModelo;
    private TextView edtDistancia, edtCorrente, edtNumeroFase, edtFios, edtFrequencia, edtKd, edtClasse, edtMultiplicador, edtDataFabricacao, edtLeituraKwh, edtLeituraKvarh, edtDisjuntor, edtNumeroElemento;

    ArrayList<String> arraySpnLado = new ArrayList<String>();
    ArrayList<String> arraySpnFaseamento = new ArrayList<String>();
    ArrayList<String> arraySpnRamal = new ArrayList<String>();
    ArrayList<String> arraySpnLocalizacao = new ArrayList<String>();
    ArrayList<String> arraySpnMedicao = new ArrayList<String>();
    ArrayList<String> arraySpnTipoMedidor = new ArrayList<String>();
    ArrayList<String> arraySpnNumeroDigitos= new ArrayList<String>();
    ArrayList<String> arraySpnMarca= new ArrayList<String>();
    ArrayList<String> arraySpnModelo= new ArrayList<String>();

    ArrayAdapter adapterLado, adapterFaseamento, adapterRamal, adapterLocalizacao, adapterMedicao, adapterTipoMedidor, adapterNumeroDigitos, adapterMarca, adapterModelo;
    ArrayList<String> spn1 = new ArrayList<String>();
    private RadioGroup  grupTipo, grupClasse, grupRamal, grupNumeroFases, grupFaseamento;
    private RadioButton radioIndustrial, radioComercial, radioResidencial;
    private RadioButton radioClasseA, radioClasseB , radioClasseC;
    private RadioButton D10, T10, Q10, S01, S02, S03, Q16, Q25, Q35, W01, W02, W03;
    private RadioButton AN, BN, CN, ABN, ACN, BCN, ABCN;
    private RadioButton radioFase1, radioFase2, radioFase3;
    private String selectedIdRamal = " ";
    private String selectedIdFase = " ";
    private EditText etDate, edtMedidor;
    private String nomeModelo="";

    SQLiteDatabase db;
    String BANCO = "banco.db";
    String TABELACONSUMIDOR = "consumidor";
    private String vaiEditar = "0", veioEdicao = "0";

    private String tipoEquipamento, numeroPlacaEquipamento, tensao, descricao, idConsumidor, idLocacao, idPoste, marcaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_consumidores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Aplicativo HCC");
        toolbar.setSubtitle("Dados do Consumidores");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        etDate = (EditText) findViewById(R.id.edtDataFabricacao);
        etDate.addTextChangedListener(Mask.insert("##/##/####", etDate));

        edtMedidor = (EditText) findViewById(R.id.edtMedidor);

        edtDistancia = (TextView) findViewById(R.id.edtDistancia);
        edtCorrente = (TextView) findViewById(R.id.edtCorrente);
        edtNumeroFase = (TextView) findViewById(R.id.edtNumeroFase);
        edtFios = (TextView) findViewById(R.id.edtFios);
        edtFrequencia = (TextView) findViewById(R.id.edtFrequencia);
        edtKd = (TextView) findViewById(R.id.edtKd);
        edtClasse = (TextView) findViewById(R.id.edtClasse);
        edtMultiplicador = (TextView) findViewById(R.id.edtMultiplicador);
        edtDataFabricacao = (TextView) findViewById(R.id.edtDataFabricacao);
        edtLeituraKwh = (TextView) findViewById(R.id.edtLeituraKwh);
        edtLeituraKvarh = (TextView) findViewById(R.id.edtLeituraKvarh);
        edtDisjuntor = (TextView) findViewById(R.id.edtDisjuntor);
        edtNumeroElemento = (TextView) findViewById(R.id.edtNumeroElemento);

        spnLado = (Spinner) findViewById(R.id.spnLado);
        spnFaseamento = (Spinner) findViewById(R.id.spnFaseamento);
        spnRamal = (Spinner) findViewById(R.id.spnRamal);
        spnLocalizacao = (Spinner) findViewById(R.id.spnLocalizacao);
        spnMedicao = (Spinner) findViewById(R.id.spnMedicao);
        spnTipoMedidor = (Spinner) findViewById(R.id.spnTipoMedidor);
        spnNumeroDigitos= (Spinner) findViewById(R.id.spnNumeroDigitos);
        spnMarca= (Spinner) findViewById(R.id.spnMarca);
        spnModelo= (Spinner) findViewById(R.id.spnModelo);

        arraySpnModelo.add("<- Selecionar Marca");

        arraySpnLado.add("Selecione o Lado");
        arraySpnLado.add("Direito");
        arraySpnLado.add("Esquerdo");

        arraySpnFaseamento.add("Faseamento");
        arraySpnFaseamento.add("AN");
        arraySpnFaseamento.add("BN");
        arraySpnFaseamento.add("CN");
        arraySpnFaseamento.add("ABN");
        arraySpnFaseamento.add("ACN");
        arraySpnFaseamento.add("BCN");
        arraySpnFaseamento.add("ABCN");

        arraySpnRamal.add("Ramal");
        arraySpnRamal.add("D10");
        arraySpnRamal.add("D16");
        arraySpnRamal.add("T16");
        arraySpnRamal.add("Q16");
        arraySpnRamal.add("Q25");
        arraySpnRamal.add("Q35");
        arraySpnRamal.add("W01");
        arraySpnRamal.add("W02");
        arraySpnRamal.add("W03");
        arraySpnRamal.add("S01");
        arraySpnRamal.add("S02");
        arraySpnRamal.add("S03");

        arraySpnLocalizacao.add("Localização");
        arraySpnLocalizacao.add("Interna");
        arraySpnLocalizacao.add("Externa");
        arraySpnLocalizacao.add("Mureta");

        arraySpnMedicao.add("Selecione o Tipo da Medição");
        arraySpnMedicao.add("Poste");
        arraySpnMedicao.add("Com Pontalete");
        arraySpnMedicao.add("Sem Pontalete");

        arraySpnTipoMedidor.add("Tipo");
        arraySpnTipoMedidor.add("Kwh");
        arraySpnTipoMedidor.add("Kvarh");
        arraySpnTipoMedidor.add("Eletrônico");

        arraySpnNumeroDigitos.add("Digitos");
        arraySpnNumeroDigitos.add("4");
        arraySpnNumeroDigitos.add("5");
        arraySpnNumeroDigitos.add("6");
        arraySpnNumeroDigitos.add("7");
        arraySpnNumeroDigitos.add("8");

        arraySpnMarca.add("Marca");
        arraySpnMarca.add("ABB");
        arraySpnMarca.add("APREL");
        arraySpnMarca.add("COMPLANT");
        arraySpnMarca.add("ELETRA");
        arraySpnMarca.add("ELO");
        arraySpnMarca.add("ELSTER");
        arraySpnMarca.add("FAE");
        arraySpnMarca.add("GALILEU");
        arraySpnMarca.add("GE");
        arraySpnMarca.add("GENUS");
        arraySpnMarca.add("LANDIS");
        arraySpnMarca.add("NANSEN");
        arraySpnMarca.add("SENTINEL");
        arraySpnMarca.add("SCHLUMB");
        arraySpnMarca.add("SCHNEIDER");
        arraySpnMarca.add("WESTINGH");

        adapterLado = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySpnLado);
        adapterLado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterFaseamento = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySpnFaseamento);
        adapterFaseamento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterRamal = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySpnRamal);
        adapterRamal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterLocalizacao = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySpnLocalizacao);
        adapterLocalizacao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterMedicao = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySpnMedicao);
        adapterMedicao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterTipoMedidor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySpnTipoMedidor);
        adapterTipoMedidor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterNumeroDigitos = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySpnNumeroDigitos);
        adapterNumeroDigitos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterMarca = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySpnMarca);
        adapterMarca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterModelo = new ArrayAdapter(CadastrarConsumidores.this, android.R.layout.simple_spinner_item, arraySpnModelo);
        adapterModelo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnLado.setAdapter(adapterLado);
        spnFaseamento.setAdapter(adapterFaseamento);
        spnRamal.setAdapter(adapterRamal);
        spnLocalizacao.setAdapter(adapterLocalizacao);
        spnMedicao.setAdapter(adapterMedicao);
        spnTipoMedidor.setAdapter(adapterTipoMedidor);
        spnNumeroDigitos.setAdapter(adapterNumeroDigitos);
        spnMarca.setAdapter(adapterMarca);
        spnModelo.setAdapter(adapterModelo);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // Verifica se existe dados enviados da pagina anterior
        if (getIntent().getStringExtra("USERTELA") != null) {
            tipoEquipamento = bundle.getString("tipo");
            numeroPlacaEquipamento = bundle.getString("placa");
            tensao = bundle.getString("tensao");
            descricao = bundle.getString("descricao");

            // Verifica se veio para edição, se sim: variavel Vaieditar recebe 1 e é pego o id do Consumidor que será editado
            if (getIntent().getStringExtra("USERTELA").equals("EDITAR")) {
                vaiEditar = "1";
                idConsumidor = bundle.getString("id");
                preencheDadosEdicaoConsumidores(idConsumidor);
            }

            // Verifica se a pagina anterior estava sendo editada ou inserida uma nova
            // Se sim, variavel veiEdicao recebe 1 (pois qunado retornar a pagina anterior, avisar o sistema que aquela pagina era uma edição)
            if (getIntent().getStringExtra("edit") != null && getIntent().getStringExtra("edit").equals("1")) {
                veioEdicao = "1";
            }
        }

        spnMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                marcaSelecionada = parent.getItemAtPosition(position).toString();
                arraySpnModelo.clear();

                if(marcaSelecionada.equals("ABB")){
                    arraySpnModelo.add("A1000");
                    arraySpnModelo.add("A1R");
                    arraySpnModelo.add("ALPHA");
                    arraySpnModelo.add("ME 21");
                    arraySpnModelo.add("ME 21A");
                    arraySpnModelo.add("M8C");
                    arraySpnModelo.add("T8L");
                    arraySpnModelo.add("T8LR");
                    spnModelo.setAdapter(adapterModelo);
                    if(!nomeModelo.equals("")){
                        spnModelo.setSelection(adapterModelo.getPosition(nomeModelo));
                    }

                } else if(marcaSelecionada.equals("APREL")){
                    arraySpnModelo.add("M5A3V 50");
                    arraySpnModelo.add("M5C3V 75");
                    arraySpnModelo.add("M8C");
                    arraySpnModelo.add("M8L");
                    arraySpnModelo.add("T4C3V");
                    arraySpnModelo.add("T8L");
                    spnModelo.setAdapter(adapterModelo);
                    if(!nomeModelo.equals("")){
                        spnModelo.setSelection(adapterModelo.getPosition(nomeModelo));
                    }

                } else if(marcaSelecionada.equals("COMPLANT")) {
                    arraySpnModelo.add("MEP01-CE2");
                    spnModelo.setAdapter(adapterModelo);
                    if(!nomeModelo.equals("")){
                        spnModelo.setSelection(adapterModelo.getPosition(nomeModelo));
                    }

                } else if(marcaSelecionada.equals("ELETRA")){
                    arraySpnModelo.add("CRONOS 6021L");
                    arraySpnModelo.add("CRONOS 7023L");
                    spnModelo.setAdapter(adapterModelo);
                    if(!nomeModelo.equals("")){
                        spnModelo.setSelection(adapterModelo.getPosition(nomeModelo));
                    }

                } else if(marcaSelecionada.equals("ELO")){
                    arraySpnModelo.add("2101 A");
                    arraySpnModelo.add("2101 L");
                    arraySpnModelo.add("2103 A");
                    arraySpnModelo.add("2103 L");
                    arraySpnModelo.add("2106");
                    arraySpnModelo.add("2106 D");
                    arraySpnModelo.add("2106 L");
                    arraySpnModelo.add("2106 LD");
                    arraySpnModelo.add("2123 D");
                    arraySpnModelo.add("2113 10");
                    arraySpnModelo.add("2113 20");
                    arraySpnModelo.add("2113 120");
                    arraySpnModelo.add("2150");
                    arraySpnModelo.add("2180");
                    arraySpnModelo.add("2190");
                    arraySpnModelo.add("MEMP");
                    spnModelo.setAdapter(adapterModelo);
                    if(!nomeModelo.equals("")){
                        spnModelo.setSelection(adapterModelo.getPosition(nomeModelo));
                    }

                } else if(marcaSelecionada.equals("ELSTER")){
                    arraySpnModelo.add("A1052");
                    arraySpnModelo.add("A1200");
                    arraySpnModelo.add("A200M");
                    arraySpnModelo.add("A 100C");
                    arraySpnModelo.add("A102 C");
                    arraySpnModelo.add("T8L");
                    arraySpnModelo.add("T8LR");
                    arraySpnModelo.add("ME21");
                    arraySpnModelo.add("ME21A");
                    arraySpnModelo.add("MR2P");
                    arraySpnModelo.add("MS2P");
                    arraySpnModelo.add("M8C");
                    spnModelo.setAdapter(adapterModelo);
                    if(!nomeModelo.equals("")){
                        spnModelo.setSelection(adapterModelo.getPosition(nomeModelo));
                    }

                } else if(marcaSelecionada.equals("FAE")){
                    arraySpnModelo.add("MF-33K");
                    arraySpnModelo.add("MF-34KA");
                    arraySpnModelo.add("MF-63K");
                    arraySpnModelo.add("MF-79G");
                    arraySpnModelo.add("MFT 04 G");
                    spnModelo.setAdapter(adapterModelo);
                    if(!nomeModelo.equals("")){
                        spnModelo.setSelection(adapterModelo.getPosition(nomeModelo));
                    }

                } else if(marcaSelecionada.equals("GALILEU")){
                    arraySpnModelo.add("M5A3V");
                    spnModelo.setAdapter(adapterModelo);
                    if(!nomeModelo.equals("")){
                        spnModelo.setSelection(adapterModelo.getPosition(nomeModelo));
                    }

                } else if(marcaSelecionada.equals("GE")){
                    arraySpnModelo.add("I-54C");
                    spnModelo.setAdapter(adapterModelo);
                    if(!nomeModelo.equals("")){
                        spnModelo.setSelection(adapterModelo.getPosition(nomeModelo));
                    }

                } else if(marcaSelecionada.equals("GENUS")){
                    arraySpnModelo.add("BF4A0");
                    spnModelo.setAdapter(adapterModelo);
                    if(!nomeModelo.equals("")){
                        spnModelo.setSelection(adapterModelo.getPosition(nomeModelo));
                    }

                } else if(marcaSelecionada.equals("LANDIS")){
                    arraySpnModelo.add("E 22A");
                    arraySpnModelo.add("E 34A");
                    arraySpnModelo.add("E 650");
                    arraySpnModelo.add("E 750");
                    arraySpnModelo.add("SAGA 1000 1681 A");
                    arraySpnModelo.add("ZMD 318 CMT 815");
                    spnModelo.setAdapter(adapterModelo);
                    if(!nomeModelo.equals("")){
                        spnModelo.setSelection(adapterModelo.getPosition(nomeModelo));
                    }

                } else if(marcaSelecionada.equals("NANSEN")){
                    arraySpnModelo.add("LUMEN MC");
                    arraySpnModelo.add("M1A-G");
                    arraySpnModelo.add("M1AT-A");
                    arraySpnModelo.add("M-2 A");
                    arraySpnModelo.add("PN5T-G");
                    arraySpnModelo.add("SPECTRUN K 20");
                    arraySpnModelo.add("SPECTRUN K 100");
                    arraySpnModelo.add("SPECTRUN K 120");
                    arraySpnModelo.add("T8L");
                    spnModelo.setAdapter(adapterModelo);
                    if(!nomeModelo.equals("")){
                        spnModelo.setSelection(adapterModelo.getPosition(nomeModelo));
                    }

                } else if(marcaSelecionada.equals("SENTINEL")){
                    arraySpnModelo.add("SS4A2D");
                    spnModelo.setAdapter(adapterModelo);
                    if(!nomeModelo.equals("")){
                        spnModelo.setSelection(adapterModelo.getPosition(nomeModelo));
                    }

                } else if(marcaSelecionada.equals("SCHLUMB")){
                    arraySpnModelo.add("FX 221");
                    arraySpnModelo.add("FY 202");
                    arraySpnModelo.add("FY201 60");
                    arraySpnModelo.add("FY201 120");
                    arraySpnModelo.add("SL 1621");
                    spnModelo.setAdapter(adapterModelo);
                    if(!nomeModelo.equals("")){
                        spnModelo.setSelection(adapterModelo.getPosition(nomeModelo));
                    }

                } else if(marcaSelecionada.equals("SCHNEIDER")){
                    arraySpnModelo.add("ION 8650C");
                    spnModelo.setAdapter(adapterModelo);
                    if(!nomeModelo.equals("")){
                        spnModelo.setSelection(adapterModelo.getPosition(nomeModelo));
                    }

                } else if(marcaSelecionada.equals("WESTINGH")){
                    arraySpnModelo.add("M8C");
                    arraySpnModelo.add("M8L");
                    arraySpnModelo.add("T8 L");
                    spnModelo.setAdapter(adapterModelo);
                    if(!nomeModelo.equals("")){
                        spnModelo.setSelection(adapterModelo.getPosition(nomeModelo));
                    }

                }
                spnModelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String modeloSelecionado = parent.getItemAtPosition(position).toString();
                        if(marcaSelecionada.equals("ABB")){
                            if(modeloSelecionado.equals("A1000")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,0");

                            }else if(modeloSelecionado.equals("A1R")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("2,60");

                            }else if(modeloSelecionado.equals("ALPHA")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("0,00");

                            }else if(modeloSelecionado.equals("M8C")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            }else if(modeloSelecionado.equals("ME 21")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            }else if(modeloSelecionado.equals("ME 21A")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            }else if(modeloSelecionado.equals("T8L")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("21,60");

                            }else if(modeloSelecionado.equals("T8LR")){
                                edtCorrente.setText("2,5/ 20");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            }
                        }else if(marcaSelecionada.equals("APREL")){
                            if(modeloSelecionado.equals("M5A3V 50")){
                                edtCorrente.setText("10/ 50");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("50");
                                edtKd.setText("2,40");

                            }else if(modeloSelecionado.equals("M5C3V 75")){
                                edtCorrente.setText("15/ 75");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,70");

                            }else if(modeloSelecionado.equals("M8C")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            }else if(modeloSelecionado.equals("M8L")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            }else if(modeloSelecionado.equals("T4C3V")){
                                edtCorrente.setText("30/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("21,60");

                            }else if(modeloSelecionado.equals("T8L")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("21,60");

                            }
                        }else if(marcaSelecionada.equals("COMPLANT")){
                            if(modeloSelecionado.equals("MEP01-CE2")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,25");

                            }
                        } else if(marcaSelecionada.equals("ELETRA")){
                            if(modeloSelecionado.equals("CRONOS 6021L")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,00");

                            }else if(modeloSelecionado.equals("CRONOS 7023L")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("2");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,00");

                            }
                        } else if(marcaSelecionada.equals("ELO")){
                            if(modeloSelecionado.equals("2101 A")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,00");

                            }else if(modeloSelecionado.equals("2101 L")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,00");

                            } else if(modeloSelecionado.equals("2103 A")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("10,00");

                            } else if(modeloSelecionado.equals("2103 L")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("2,00");

                            } else if(modeloSelecionado.equals("2106")){
                                edtCorrente.setText("2,5/ 10");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,00");

                            } else if(modeloSelecionado.equals("2106 D")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,00");

                            } else if(modeloSelecionado.equals("2106 LD")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("0,20");

                            } else if(modeloSelecionado.equals("2123 D")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("4,00");

                            } else if(modeloSelecionado.equals("2113 10")){
                                edtCorrente.setText("2,5/ 10");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("0,20");

                            } else if(modeloSelecionado.equals("2113 20")){
                                edtCorrente.setText("2,5/ 20");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("0,20");

                            } else if(modeloSelecionado.equals("2113 120")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("0,40");

                            } else if(modeloSelecionado.equals("2150")){
                                edtCorrente.setText("2,5/ 10");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("0,20");

                            } else if(modeloSelecionado.equals("2180")){
                                edtCorrente.setText("2,5/ 20");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("0,20");

                            } else if(modeloSelecionado.equals("2190")){
                                edtCorrente.setText("2,5/ 10");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("0,20");

                            } else if(modeloSelecionado.equals("MEMP")){
                                edtCorrente.setText("2,5/ 20");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("0,00");

                            }
                        }else if(marcaSelecionada.equals("ELSTER")){
                            if(modeloSelecionado.equals("A1052")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,00");

                            }else if(modeloSelecionado.equals("A1200")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,00");

                            } else if(modeloSelecionado.equals("A200M")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("0,62");

                            } else if(modeloSelecionado.equals("A 100C")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,00");

                            } else if(modeloSelecionado.equals("A102 C")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,00");

                            } else if(modeloSelecionado.equals("T8L")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("21,60");

                            } else if(modeloSelecionado.equals("T8LR")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("21,60");

                            } else if(modeloSelecionado.equals("ME21")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            } else if(modeloSelecionado.equals("ME21A")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            } else if(modeloSelecionado.equals("MR2P")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("1");
                                edtFrequencia.setText("60");
                                edtKd.setText("4,16");

                            } else if(modeloSelecionado.equals("MS2P")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("4,16");

                            } else if(modeloSelecionado.equals("M8C")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            }
                        }else if(marcaSelecionada.equals("FAE")){
                            if(modeloSelecionado.equals("MF-33K")){
                                edtCorrente.setText("10/ 50");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("50");
                                edtKd.setText("2,50");

                            }else if(modeloSelecionado.equals("MF-34KA")){
                                edtCorrente.setText("10/ 50");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("2,50");

                            } else if(modeloSelecionado.equals("MF-63K")){
                                edtCorrente.setText("10/ 40");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("2,77");

                            } else if(modeloSelecionado.equals("MF-79G")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            } else if(modeloSelecionado.equals("MFT 04 G")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("21,60");

                            }
                        }else if(marcaSelecionada.equals("GALILEU")){
                            if(modeloSelecionado.equals("M5A3V")){
                                edtCorrente.setText("10/ 50");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            }
                        }else if(marcaSelecionada.equals("GE")){
                            if(modeloSelecionado.equals("I-54C")){
                                edtCorrente.setText("10/ 40");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("21,60");

                            }
                        }else if(marcaSelecionada.equals("GENUS")){
                            if(modeloSelecionado.equals("BF4A0")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("0,31");

                            }
                        } else if(marcaSelecionada.equals("LANDIS")){
                            if(modeloSelecionado.equals("E 22A")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,00");

                            }else if(modeloSelecionado.equals("E 34A")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,00");

                            } else if(modeloSelecionado.equals("E 650")){
                                edtCorrente.setText("2,5/ 10");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("0,20");

                            } else if(modeloSelecionado.equals("E 750")){
                                edtCorrente.setText("2,5/ 10");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("0,30");

                            } else if(modeloSelecionado.equals("SAGA 1000 1681 A")){
                                edtCorrente.setText("2,5/ 10");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("0,30");

                            } else if(modeloSelecionado.equals("ZMD 318 CMT 815")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,00");

                            }
                        } else if(marcaSelecionada.equals("NANSEN")){
                            if(modeloSelecionado.equals("LUMEN MC")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,00");

                            }else if(modeloSelecionado.equals("M1A-G")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("3");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            } else if(modeloSelecionado.equals("M1AT-A")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            } else if(modeloSelecionado.equals("M-2 A")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            } else if(modeloSelecionado.equals("PN5T-G")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("21,60");

                            } else if(modeloSelecionado.equals("SPECTRUN K 20")){
                                edtCorrente.setText("2,5/ 20");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("0,30");

                            } else if(modeloSelecionado.equals("SPECTRUN K 100")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            } else if(modeloSelecionado.equals("SPECTRUN K 120")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,80");

                            } else if(modeloSelecionado.equals("T8L")){
                                edtCorrente.setText("3/ 10");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("1");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            }
                        }else if(marcaSelecionada.equals("SENTINEL")){
                            if(modeloSelecionado.equals("SS4A2D")){
                                edtCorrente.setText("2,5/ 10");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,80");

                            }
                        }else if(marcaSelecionada.equals("SCHLUMB")){
                            if(modeloSelecionado.equals("FX 221")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            }else if(modeloSelecionado.equals("FY 202")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("21,60");

                            } else if(modeloSelecionado.equals("FY201 60")){
                                edtCorrente.setText("15/ 60");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("10,80");

                            } else if(modeloSelecionado.equals("FY201 120")){
                                edtCorrente.setText("30/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("14,40");

                                arraySpnModelo.add("");

                            } else if(modeloSelecionado.equals("SL 1621")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            }
                        }else if(marcaSelecionada.equals("SCHNEIDER")){
                            if(modeloSelecionado.equals("ION 8650C")){
                                edtCorrente.setText("2,5/ 10");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("1,80");

                            }
                        }else if(marcaSelecionada.equals("WESTINGH")){
                            if(modeloSelecionado.equals("M8C")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            }else if(modeloSelecionado.equals("M8L")){
                                edtCorrente.setText("15/ 100");
                                edtNumeroFase.setText("1");
                                edtNumeroElemento.setText("1");
                                edtFios.setText("2");
                                edtFrequencia.setText("60");
                                edtKd.setText("3,60");

                            } else if(modeloSelecionado.equals("T8 L")){
                                edtCorrente.setText("15/ 120");
                                edtNumeroFase.setText("3");
                                edtNumeroElemento.setText("3");
                                edtFios.setText("4");
                                edtFrequencia.setText("60");
                                edtKd.setText("21,60");

                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //Another interface callback
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });
    }

    //Metodo que pega ação do botçao voltar, no toolbar bem em cima da tela
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Tem certeza que deseja sair desta aba? Os dados ainda não foram salvos");
                alertDialogBuilder.setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                //Verifica se a pagina anterior estava sendo editada
                                if (veioEdicao.equals("1")) { // se sim, manda um put extra avisando para continuar a edição
                                    Intent intent = new Intent(CadastrarConsumidores.this, CadastrarEquipamento.class);
                                    intent.putExtra("USERTELA", "EDITAR");
                                    CadastrarConsumidores.this.startActivity(intent);
                                    finish();
                                } else { // se não, envia os dados que estavam sendo inseridos(Caso haja)
                                    Intent intent = new Intent(CadastrarConsumidores.this, CadastrarEquipamento.class);
                                    intent.putExtra("USERTELA", "10");
                                    intent.putExtra("tipo", tipoEquipamento);
                                    intent.putExtra("placa", numeroPlacaEquipamento);
                                    intent.putExtra("descricao", descricao);
                                    intent.putExtra("tensao", tensao);
                                    CadastrarConsumidores.this.startActivity(intent);
                                    finish();
                                }
                            }
                        });
                alertDialogBuilder.setNegativeButton("Não",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            default:
                break;
        }
        return true;
    }

    //Metodo responsavel em buscar no banco os dados do consumidor selecionado inseri-los nos seus devidos campos.
    public void preencheDadosEdicaoConsumidores(String idConsumidor) {
        String medidorEdt = "",lado = "",faseamento = "",ramal = "",distancia = "",localizacao = "",medidorSpn = "";
        String tipoMedidor = "",marca = "",modelo = "",corrente = "",numeroFases = "",numeroElemento = "";
        String frequencia = "",fios = "",kd = "",classe = "",numeroDigitos = "",multiplicador = "";
        String dataFabricacao = "",leituraKwh = "",leituraKaeh = "",disjuntor = "";
        db = openOrCreateDatabase(BANCO, Context.MODE_PRIVATE, null);
        Cursor linhas = db.rawQuery("SELECT * FROM " + TABELACONSUMIDOR + " WHERE ID = " + idConsumidor + ";", null);
        if (linhas.moveToFirst()) {
            do {
                lado = linhas.getString(1);
                ramal = linhas.getString(4);
                medidorEdt = linhas.getString(6);
                faseamento = linhas.getString(7);
                distancia = linhas.getString(8);
                localizacao = linhas.getString(9);
                medidorSpn = linhas.getString(10);
                tipoMedidor = linhas.getString(11);
                marca = linhas.getString(12);
                nomeModelo = linhas.getString(13);
                corrente = linhas.getString(14);
                numeroFases = linhas.getString(15);
                numeroElemento = linhas.getString(16);
                frequencia = linhas.getString(17);
                fios = linhas.getString(18);
                kd = linhas.getString(19);
                classe = linhas.getString(3);
                numeroDigitos = linhas.getString(20);
                multiplicador = linhas.getString(21);
                dataFabricacao = linhas.getString(22);
                leituraKwh = linhas.getString(23);
                leituraKaeh = linhas.getString(24);
                disjuntor = linhas.getString(25);
            }
            while (linhas.moveToNext());
            linhas.close();
        }
        db.close();

        edtMedidor.setText(medidorEdt);
        edtDistancia.setText(distancia);
        edtCorrente.setText(corrente);
        edtNumeroFase.setText(numeroFases);
        edtFios.setText(fios);
        edtFrequencia.setText(frequencia);
        edtKd.setText(kd);
        edtClasse.setText(classe);
        edtMultiplicador.setText(multiplicador);
        edtDataFabricacao.setText(dataFabricacao);
        edtLeituraKwh.setText(leituraKwh);
        edtLeituraKvarh.setText(leituraKaeh);
        edtDisjuntor.setText(disjuntor);
        edtNumeroElemento.setText(numeroElemento);

        Log.d("teste","Modelo: "+nomeModelo);

        if (lado.equals("D")) {
            lado = "Direito";
        }else if (lado.equals("E")) {
            lado = "Esquerdo";
        }

        spnLado.setSelection(adapterLado.getPosition(lado));
        spnFaseamento.setSelection(adapterFaseamento.getPosition(faseamento));
        spnRamal.setSelection(adapterRamal.getPosition(ramal));
        spnLocalizacao.setSelection(adapterLocalizacao.getPosition(localizacao));
        spnMedicao.setSelection(adapterMedicao.getPosition(medidorSpn));
        spnTipoMedidor.setSelection(adapterTipoMedidor.getPosition(tipoMedidor));
        spnNumeroDigitos.setSelection(adapterNumeroDigitos.getPosition(numeroDigitos));
        spnMarca.setSelection(adapterMarca.getPosition(marca));
        //spnModelo.setSelection(adapterModelo.getPosition(modelo));

    }

    //Pega o clique do botão e chama o metodo de inserção no banco
    public void salvaConsumidor(View v) {
        salvaDadosConsumidor();
    }

    //Metodo que salva os dados no banco
    private void salvaDadosConsumidor() {
        SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        idPoste = sharedpreferences.getString("idPosteKey", null);
        idLocacao = sharedpreferences.getString("idLocacaoKey", null);


        String medidorEdt = "";
        String lado = "";
        String faseamento = "";
        String ramal = "";
        String distancia = "";
        String localizacao = "";
        String medidorSpn = "";
        String tipoMedidor = "";
        String marca = "";
        String modelo = "";
        String corrente = "";
        String numeroFases = "";
        String numeroElemento = "";
        String frequencia = "";
        String fios = "";
        String kd = "";
        String classe = "";
        String numeroDigitos = "";
        String multiplicador = "";
        String dataFabricacao = "";
        String leituraKwh = "";
        String leituraKaeh = "";
        String disjuntor = "";

        medidorEdt = edtMedidor.getText().toString();
        lado = spnLado.getSelectedItem().toString();
        faseamento = spnFaseamento.getSelectedItem().toString();
        ramal = spnRamal.getSelectedItem().toString();
        distancia = edtDistancia.getText().toString();
        localizacao = spnLocalizacao.getSelectedItem().toString();
        medidorSpn = spnMedicao.getSelectedItem().toString();
        tipoMedidor = spnTipoMedidor.getSelectedItem().toString();
        marca = spnMarca.getSelectedItem().toString();
        if(spnModelo.getSelectedItem()!=null){
            modelo = spnModelo.getSelectedItem().toString();
        }
        corrente = edtCorrente.getText().toString();
        numeroFases = edtNumeroFase.getText().toString();
        numeroElemento = edtNumeroElemento.getText().toString();
        fios = edtFios.getText().toString();
        frequencia = edtFrequencia.getText().toString();
        kd = edtKd.getText().toString();
        classe = edtClasse.getText().toString();
        numeroDigitos = spnNumeroDigitos.getSelectedItem().toString();
        multiplicador = edtMultiplicador.getText().toString();
        dataFabricacao = edtDataFabricacao.getText().toString();
        leituraKwh = edtLeituraKwh.getText().toString();
        leituraKaeh = edtLeituraKvarh.getText().toString();
        disjuntor = edtDisjuntor.getText().toString();

        //int selectedIdLado = grupLado.getCheckedRadioButtonId();
        //int selectedIdTipo = grupTipo.getCheckedRadioButtonId();
        //int selectedIdClasse = grupClasse.getCheckedRadioButtonId();
        //int selectedIdFases = grupNumeroFases.getCheckedRadioButtonId();

        if (lado.equals("Selecione o Lado")) {
            lado = "";
        } else if (lado.equals("Direito")) {
            lado = "D";
        }else if (lado.equals("Esquerdo")) {
            lado = "E";
        }

/*
        if (selectedIdTipo == radioIndustrial.getId()) {
            tipo = "I";
        } else if (selectedIdTipo == radioComercial.getId()) {
            tipo = "C";
        } else if (selectedIdTipo == radioResidencial.getId()) {
            tipo = "R";
        }

        if (selectedIdClasse == radioClasseA.getId()) {
            classe = "A";
        } else if (selectedIdClasse == radioClasseB.getId()) {
            classe = "B";
        } else if (selectedIdClasse == radioClasseC.getId()) {
            classe = "C";
        }
*/

//tipo.equals(" ")||
        //Verifica os itens obrigatórios
        if (lado.equals("")) {
            Toast.makeText(getApplicationContext(), "Por Favor! Insira os dados obrigatórios!!", Toast.LENGTH_SHORT).show();
        } else {

            //Verifica se é uma edição
            if (vaiEditar.equals("1")) { //se for, faz update
                db = openOrCreateDatabase(BANCO, Context.MODE_PRIVATE, null);
                ContentValues values = new ContentValues();
                values.put("MEDIDOREDT", medidorEdt);
                values.put("LADO", lado);
                values.put("FASEAMENTO", faseamento);
                values.put("RAMAL", ramal);
                values.put("DISTANCIA", distancia);
                values.put("LOCALIZACAO", localizacao);
                values.put("MEDIDORSPN", medidorSpn);
                values.put("TIPOMEDIDOR", tipoMedidor);
                values.put("MARCA", marca);
                values.put("MODELO", modelo);
                values.put("CORRENTE", corrente);
                values.put("NUMEROFASES", numeroFases);
                values.put("NUMEROELEMENTOS", numeroElemento);
                values.put("FREQUENCIA", frequencia);
                values.put("CLASSE", classe);
                values.put("FIO", fios);
                values.put("KD", kd);
                values.put("NUMERODIGITOS", numeroDigitos);
                values.put("MULTIPLICADOR", multiplicador);
                values.put("DATAFABRICACAO", dataFabricacao);
                values.put("LEITURAKWH", leituraKwh);
                values.put("LEITURAKEAH", leituraKaeh);
                values.put("DISJUNTOR", disjuntor);
                db.update(TABELACONSUMIDOR, values, "ID=" + idConsumidor, null);
                db.close();

            } else { //se não for, faz um insert
                db = openOrCreateDatabase(BANCO, Context.MODE_PRIVATE, null);
                ContentValues values = new ContentValues();
                values.put("MEDIDOREDT", medidorEdt);
                values.put("LADO", lado);
                values.put("FASEAMENTO", faseamento);
                values.put("RAMAL", ramal);
                values.put("DISTANCIA", distancia);
                values.put("LOCALIZACAO", localizacao);
                values.put("MEDIDORSPN", medidorSpn);
                values.put("TIPOMEDIDOR", tipoMedidor);
                values.put("MARCA", marca);
                values.put("MODELO", modelo);
                values.put("CORRENTE", corrente);
                values.put("NUMEROFASES", numeroFases);
                values.put("NUMEROELEMENTOS", numeroElemento);
                values.put("FREQUENCIA", frequencia);
                values.put("CLASSE", classe);
                values.put("FIO", fios);
                values.put("KD", kd);
                values.put("NUMERODIGITOS", numeroDigitos);
                values.put("MULTIPLICADOR", multiplicador);
                values.put("DATAFABRICACAO", dataFabricacao);
                values.put("LEITURAKWH", leituraKwh);
                values.put("LEITURAKEAH", leituraKaeh);
                values.put("DISJUNTOR", disjuntor);
                values.put("IDPOSTE", idPoste);

                db.insert(TABELACONSUMIDOR, null, values);
                db.close();
            }

            //Verifica se a pagina anterior estava sendo editada
            if (veioEdicao.equals("1")) { // se sim, manda um put extra avisando para continuar a edição
                Intent intent = new Intent(this, CadastrarEquipamento.class);
                intent.putExtra("USERTELA", "EDITAR");
                this.startActivity(intent);
                finish();
            } else { // se não, envia os dados que estavam sendo inseridos(Caso haja)
                Intent intent = new Intent(this, CadastrarEquipamento.class);
                intent.putExtra("USERTELA", "10");
                intent.putExtra("tipo", tipoEquipamento);
                intent.putExtra("placa", numeroPlacaEquipamento);
                intent.putExtra("descricao", descricao);
                intent.putExtra("tensao", tensao);
                this.startActivity(intent);
                finish();
            }
        }
    }

    //Metodo responsavel por pegar ação do botão nativo "Voltar" do smartfone
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Tem certeza que deseja sair desta aba? Os dados ainda não foram salvos");
        alertDialogBuilder.setPositiveButton("Sim",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Verifica se a pagina anterior estava sendo editada
                        if (veioEdicao.equals("1")) { // se sim, manda um put extra avisando para continuar a edição
                            Intent intent = new Intent(CadastrarConsumidores.this, CadastrarEquipamento.class);
                            intent.putExtra("USERTELA", "EDITAR");
                            CadastrarConsumidores.this.startActivity(intent);
                            finish();
                        } else { // se não, envia os dados que estavam sendo inseridos(Caso haja)
                            Intent intent = new Intent(CadastrarConsumidores.this, CadastrarEquipamento.class);
                            intent.putExtra("USERTELA", "10");
                            intent.putExtra("tipo", tipoEquipamento);
                            intent.putExtra("placa", numeroPlacaEquipamento);
                            intent.putExtra("descricao", descricao);
                            intent.putExtra("tensao", tensao);

                            CadastrarConsumidores.this.startActivity(intent);
                            finish();
                        }
                        CadastrarConsumidores.super.onBackPressed();
                    }
                });
        alertDialogBuilder.setNegativeButton("Não",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void D10(View v){
        selectedIdRamal = "D10";

        //D10.setChecked(false);
        T10.setChecked(false);
        Q10.setChecked(false);
        Q16.setChecked(false);
        Q25.setChecked(false);
        Q35.setChecked(false);
        W01.setChecked(false);
        W02.setChecked(false);
        W03.setChecked(false);
        S01.setChecked(false);
        S02.setChecked(false);
        S03.setChecked(false);
    }
    public void T10(View v){
        selectedIdRamal = "T10";

        D10.setChecked(false);
        //T10.setChecked(false);
        Q10.setChecked(false);
        Q16.setChecked(false);
        Q25.setChecked(false);
        Q35.setChecked(false);
        W01.setChecked(false);
        W02.setChecked(false);
        W03.setChecked(false);
        S01.setChecked(false);
        S02.setChecked(false);
        S03.setChecked(false);
    }

    public void Q10(View v){
        selectedIdRamal = "Q10";

        D10.setChecked(false);
        T10.setChecked(false);
        //Q10.setChecked(false);
        Q16.setChecked(false);
        Q25.setChecked(false);
        Q35.setChecked(false);
        W01.setChecked(false);
        W02.setChecked(false);
        W03.setChecked(false);
        S01.setChecked(false);
        S02.setChecked(false);
        S03.setChecked(false);
    }

    public void Q16(View v){
        selectedIdRamal = "Q16";

        D10.setChecked(false);
        T10.setChecked(false);
        Q10.setChecked(false);
        //Q16.setChecked(false);
        Q25.setChecked(false);
        Q35.setChecked(false);
        W01.setChecked(false);
        W02.setChecked(false);
        W03.setChecked(false);
        S01.setChecked(false);
        S02.setChecked(false);
        S03.setChecked(false);
    }

    public void Q25(View v){
        selectedIdRamal = "Q25";

        D10.setChecked(false);
        T10.setChecked(false);
        Q10.setChecked(false);
        Q16.setChecked(false);
        //Q25.setChecked(false);
        Q35.setChecked(false);
        W01.setChecked(false);
        W02.setChecked(false);
        W03.setChecked(false);
        S01.setChecked(false);
        S02.setChecked(false);
        S03.setChecked(false);
    }
    public void Q35(View v){
        selectedIdRamal = "Q35";

        D10.setChecked(false);
        T10.setChecked(false);
        Q10.setChecked(false);
        Q16.setChecked(false);
        Q25.setChecked(false);
        //Q35.setChecked(false);
        W01.setChecked(false);
        W02.setChecked(false);
        W03.setChecked(false);
        S01.setChecked(false);
        S02.setChecked(false);
        S03.setChecked(false);
    }

    public void W01(View v){
        selectedIdRamal = "W01";

        D10.setChecked(false);
        T10.setChecked(false);
        Q10.setChecked(false);
        Q16.setChecked(false);
        Q25.setChecked(false);
        Q35.setChecked(false);
        //W01.setChecked(false);
        W02.setChecked(false);
        W03.setChecked(false);
        S01.setChecked(false);
        S02.setChecked(false);
        S03.setChecked(false);
    }

    public void W02(View v){
        selectedIdRamal = "W02";

        D10.setChecked(false);
        T10.setChecked(false);
        Q10.setChecked(false);
        Q16.setChecked(false);
        Q25.setChecked(false);
        Q35.setChecked(false);
        W01.setChecked(false);
        //W02.setChecked(false);
        W03.setChecked(false);
        S01.setChecked(false);
        S02.setChecked(false);
        S03.setChecked(false);
    }

    public void W03(View v){
        selectedIdRamal = "W03";

        D10.setChecked(false);
        T10.setChecked(false);
        Q10.setChecked(false);
        Q16.setChecked(false);
        Q25.setChecked(false);
        Q35.setChecked(false);
        W01.setChecked(false);
        W02.setChecked(false);
        //W03.setChecked(false);
        S01.setChecked(false);
        S02.setChecked(false);
        S03.setChecked(false);
    }

    public void S01(View v){
        selectedIdRamal = "S01";

        D10.setChecked(false);
        T10.setChecked(false);
        Q10.setChecked(false);
        Q16.setChecked(false);
        Q25.setChecked(false);
        Q35.setChecked(false);
        W01.setChecked(false);
        W02.setChecked(false);
        W03.setChecked(false);
        //S01.setChecked(false);
        S02.setChecked(false);
        S03.setChecked(false);
    }

    public void S02(View v){
        selectedIdRamal = "S02";

        D10.setChecked(false);
        T10.setChecked(false);
        Q10.setChecked(false);
        Q16.setChecked(false);
        Q25.setChecked(false);
        Q35.setChecked(false);
        W01.setChecked(false);
        W02.setChecked(false);
        W03.setChecked(false);
        S01.setChecked(false);
        //S02.setChecked(false);
        S03.setChecked(false);
    }

    public void S03(View v){
        selectedIdRamal = "S03";

        D10.setChecked(false);
        T10.setChecked(false);
        Q10.setChecked(false);
        Q16.setChecked(false);
        Q25.setChecked(false);
        Q35.setChecked(false);
        W01.setChecked(false);
        W02.setChecked(false);
        W03.setChecked(false);
        S01.setChecked(false);
        S02.setChecked(false);
        //S03.setChecked(false);
    }

    public void AN(View v){
        selectedIdFase = "AN";

        //AN.setChecked(false);
        BN.setChecked(false);
        CN.setChecked(false);
        ABN.setChecked(false);
        ACN.setChecked(false);
        BCN.setChecked(false);
        ABCN.setChecked(false);
    }

    public void BN(View v){
        selectedIdFase = "BN";

        AN.setChecked(false);
        //BN.setChecked(false);
        CN.setChecked(false);
        ABN.setChecked(false);
        ACN.setChecked(false);
        BCN.setChecked(false);
        ABCN.setChecked(false);
    }

    public void CN(View v){
        selectedIdFase = "CN";

        AN.setChecked(false);
        BN.setChecked(false);
        //CN.setChecked(false);
        ABN.setChecked(false);
        ACN.setChecked(false);
        BCN.setChecked(false);
        ABCN.setChecked(false);
    }

    public void ABN(View v){
        selectedIdFase = "ABN";

        AN.setChecked(false);
        BN.setChecked(false);
        CN.setChecked(false);
        //ABN.setChecked(false);
        ACN.setChecked(false);
        BCN.setChecked(false);
        ABCN.setChecked(false);
    }

    public void ACN(View v){
        selectedIdFase = "ACN";

        AN.setChecked(false);
        BN.setChecked(false);
        CN.setChecked(false);
        ABN.setChecked(false);
        //ACN.setChecked(false);
        BCN.setChecked(false);
        ABCN.setChecked(false);
    }

    public void BCN(View v){
        selectedIdFase = "BCN";

        AN.setChecked(false);
        BN.setChecked(false);
        CN.setChecked(false);
        ABN.setChecked(false);
        ACN.setChecked(false);
        //BCN.setChecked(false);
        ABCN.setChecked(false);
    }

    public void ABCN(View v){
        selectedIdFase = "ABCN";

        AN.setChecked(false);
        BN.setChecked(false);
        CN.setChecked(false);
        ABN.setChecked(false);
        ACN.setChecked(false);
        BCN.setChecked(false);
        //ABCN.setChecked(false);
    }
}
