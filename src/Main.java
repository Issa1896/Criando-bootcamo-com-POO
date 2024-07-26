import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

abstract class Conteudo {
    protected String titulo;
    protected String descricao;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public abstract double calcularXp();
}

class Curso extends Conteudo {
    private int cargaHoraria;

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    @Override
    public double calcularXp() {
        return cargaHoraria * 10;
    }

    @Override
    public String toString() {
        return "Curso: " + getTitulo() + " - " + getDescricao() + " - Carga Horária: " + getCargaHoraria();
    }
}

class Mentoria extends Conteudo {
    private LocalDate data;

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public double calcularXp() {
        return 20d;
    }

    @Override
    public String toString() {
        return "Mentoria: " + getTitulo() + " - " + getDescricao() + " - Data: " + getData();
    }
}

class Dev {
    private String nome;
    private Set<Conteudo> conteudosInscritos = new HashSet<>();
    private Set<Conteudo> conteudosConcluidos = new HashSet<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Conteudo> getConteudosInscritos() {
        return conteudosInscritos;
    }

    public Set<Conteudo> getConteudosConcluidos() {
        return conteudosConcluidos;
    }

    public void inscreverBootcamp(Bootcamp bootcamp) {
        this.conteudosInscritos.addAll(bootcamp.getConteudos());
        bootcamp.getDevsInscritos().add(this);
    }

    public void progredir() {
        Optional<Conteudo> conteudo = this.conteudosInscritos.stream().findFirst();
        if (conteudo.isPresent()) {
            this.conteudosConcluidos.add(conteudo.get());
            this.conteudosInscritos.remove(conteudo.get());
        }
    }

    public double calcularTotalXp() {
        return this.conteudosConcluidos
                .stream()
                .mapToDouble(Conteudo::calcularXp)
                .sum();
    }

    @Override
    public String toString() {
        return "Dev: " + nome + "\nInscritos: " + conteudosInscritos + "\nConcluídos: " + conteudosConcluidos;
    }
}

class Bootcamp {
    private String nome;
    private String descricao;
    private LocalDate dataInicio = LocalDate.now();
    private LocalDate dataFim = dataInicio.plusDays(45); // duração de 45 dias como exemplo
    private Set<Conteudo> conteudos = new HashSet<>();
    private Set<Dev> devsInscritos = new HashSet<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Set<Conteudo> getConteudos() {
        return conteudos;
    }

    public void setConteudos(Set<Conteudo> conteudos) {
        this.conteudos = conteudos;
    }

    public Set<Dev> getDevsInscritos() {
        return devsInscritos;
    }

    public void setDevsInscritos(Set<Dev> devsInscritos) {
        this.devsInscritos = devsInscritos;
    }

    @Override
    public String toString() {
        return "Bootcamp: " + nome + " - " + descricao + "\nConteúdos: " + conteudos;
    }
}

public class Main {
    public static void main(String[] args) {
        Curso cursoJava = new Curso();
        cursoJava.setTitulo("Curso de Java");
        cursoJava.setDescricao("Aprenda Java do básico ao avançado");
        cursoJava.setCargaHoraria(80);

        Mentoria mentoriaJava = new Mentoria();
        mentoriaJava.setTitulo("Mentoria de Java");
        mentoriaJava.setDescricao("Acompanhe o aprendizado com um mentor");
        mentoriaJava.setData(LocalDate.now());

        Bootcamp bootcamp = new Bootcamp();
        bootcamp.setNome("Bootcamp Java Developer");
        bootcamp.setDescricao("Aprenda Java e se torne um desenvolvedor completo");
        bootcamp.getConteudos().add(cursoJava);
        bootcamp.getConteudos().add(mentoriaJava);

        Dev devJoao = new Dev();
        devJoao.setNome("João");
        devJoao.inscreverBootcamp(bootcamp);
        System.out.println(devJoao);

        devJoao.progredir();
        System.out.println(devJoao);

        System.out.println("XP total: " + devJoao.calcularTotalXp());
    }
}
