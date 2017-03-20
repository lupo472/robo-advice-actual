package it.uiip.digitalgarage.roboadvice.test.service;

import it.uiip.digitalgarage.roboadvice.RoboadviceApplication;
import it.uiip.digitalgarage.roboadvice.logic.operator.DefaultStrategyOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.DefaultStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.DefaultStrategyRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.controller.DefaultStrategyController;
import it.uiip.digitalgarage.roboadvice.service.dto.DefaultStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboadviceApplication.class)
public class DefaultStrategyOperatorTest {

    @InjectMocks
    @Autowired
    private DefaultStrategyOperator defaultStrategyOp;

    @Mock
    private DefaultStrategyRepository defaultStrategyRep;

    @Mock
    private UserRepository userRep;

    private static UserEntity user;
    private static Authentication auth;

    @BeforeClass
    public static void setUpAuth() {
        user = new UserEntity();
        user.setId(new Long(2));
        user.setEmail("luca@antilici.it");
        user.setPassword("pippo123");
        user.setDate(LocalDate.now());
        user.setLastUpdate(LocalDate.now());

        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        String role = "USER";
        authorities.add(new SimpleGrantedAuthority(role));
        User principal = new User("luca@antilici.it", "", authorities);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(principal, "", authorities);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        auth = SecurityContextHolder.getContext().getAuthentication();
    }

    @AfterClass
    public static void detachResources() {
        SecurityContextHolder.clearContext();
        System.out.println("Cleared context");
    }

    @Before
    public void setUpMock() {
        MockitoAnnotations.initMocks(this);
        when(userRep.findByEmail("luca@antilici.it")).thenReturn(user);

    }

    @Test
    public void getDefaultStrategySetTestSuccess() {
       /* List<DefaultStrategyEntity> resultList = new ArrayList<>();
        DefaultStrategyEntity defaultStrategyEntity = new DefaultStrategyEntity();
        AssetClassEntity assetClassEntity = new AssetClassEntity();
        assetClassEntity.setId(new Long(1));
        assetClassEntity.setName();
        defaultStrategyEntity.setId(new Long(1));
        defaultStrategyEntity.setName("risky");
        defaultStrategyEntity.setAssetClass(assetClassEntity);

        GenericResponse<?> response = this.defaultStrategyOp.getDefaultStrategySet();
        List<?> list = (List<?>) response.getData();
        DefaultStrategyDTO strategy = (DefaultStrategyDTO) list.get(0);
        assertEquals(1, response.getResponse());
        assertFalse(list.isEmpty());
        assertFalse(strategy.getList().isEmpty());*/
    }

}