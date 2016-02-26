package tiscon1.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import tiscon1.model.Genre;
import tiscon1.repository.GenreRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fujiwara
 */
@Component
public class LayoutInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    GenreRepository genreRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        List<Genre> movieGenres = genreRepository.findMovieGenres();
        List<Genre> musicGenres = genreRepository.findMusicGenres();
        modelAndView.getModel().put("genres", genreRepository.findAll());
        modelAndView.getModel().put("movieGenres", movieGenres);
        modelAndView.getModel().put("musicGenres", musicGenres);
        modelAndView.getModel().put("principal", request.getAttribute("principal"));

        // メニュー表示用にListを分割
        List<List<Genre>> dividedMovieGenres = new ArrayList<List<Genre>>();
//        for (int i = 0; i <= movieGenres.size() / 15; i++) {
//            if (movieGenres.size() > (i + 1) * 15) {
//                dividedMovieGenres.add(movieGenres.subList(i * 15, (i + 1) * 15));
//            } else {
//                dividedMovieGenres.add(movieGenres.subList(i * 15, movieGenres.size()));
//            }
//        }

        List<Genre> musicCountry = new ArrayList<>();
        List<Genre> musicOther = new ArrayList<>();
        movieGenres.stream().forEach(o -> {
            if(o.getName().equals("日本映画") ||
                    o.getName().equals("アフリカ") ||
                    o.getName().equals("外国") ||
                    o.getName().equals("韓国映画") ||
                    o.getName().equals("中東") ||
                    o.getName().equals("インド地方音楽") ||
                    o.getName().equals("ロシア"))
            {
                musicCountry.add(o);
            }else{
                musicOther.add(o);
            }
        });

        //国別のカテゴリを設定
        dividedMovieGenres.add(musicCountry);

        //ジャンル別のカテゴリを設定
        for (int i = 0 ; i <= musicOther.size() / 15 ; i++){
            if(musicOther.size() > (i + 1) * 15){
                dividedMovieGenres.add(musicOther.subList(i * 15, (i +1) * 15));
            }else{
                dividedMovieGenres.add(musicOther.subList(i * 15, musicOther.size()));
            }
        }

        modelAndView.getModel().put("dividedMovieGenres", dividedMovieGenres);

        List<List<Genre>> dividedMusicGenres = new ArrayList<List<Genre>>();
        for (int i = 0; i <= musicGenres.size() / 15; i++) {
            if (musicGenres.size() > (i + 1) * 15) {
                dividedMusicGenres.add(musicGenres.subList(i * 15, (i + 1) * 15));
            } else {
                dividedMusicGenres.add(musicGenres.subList(i * 15, musicGenres.size()));
            }
        }
        modelAndView.getModel().put("dividedMusicGenres", dividedMusicGenres);
    }

    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }
}
