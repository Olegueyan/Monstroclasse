package net.olegueyan.monstroclasse.common;

import net.olegueyan.monstroclasse.Main;
import net.olegueyan.monstroclasse.entity.Avatar;
import net.olegueyan.monstroclasse.entity.Pupil;
import net.olegueyan.monstroclasse.event.SqlEventListener;
import net.olegueyan.monstroclasse.event.SqlEventType;
import net.olegueyan.monstroclasse.function.Selector;
import net.olegueyan.monstroclasse.repository.AvatarRepository;
import net.olegueyan.monstroclasse.repository.PupilRepository;

import java.util.Comparator;
import java.util.List;

/**
 * Interactive Memory of Monstroclasse
 * <br>
 * Can be updated when something happens
 * <br>
 * Store data that can be changed
 */
public class MonstroclasseMemory implements SqlEventListener
{
    private static final MonstroclasseMemory INSTANCE = new MonstroclasseMemory();

    // ***************************************************************
    // MonstroclasseMemory - ATTRIBUTES
    // ***************************************************************

    // ------- All the Pupils registered ------- //
    private List<Pupil> pupils;
    // ----------------------------------------- //

    // ------- All avatar available ------- //
    private Selector<Avatar> avatarSelector;
    // ------------------------------------ //

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // MonstroclasseMemory - CONSTRUCTOR
    // ***************************************************************

    public MonstroclasseMemory()
    {
        /*
            Getting all Pupils from the database using PupilRepository
         */
        this.pupils = PupilRepository.readAll();
        this.pupils.sort(Comparator.comparing(Pupil::getName));

        /*
            Getting all avatars available from the database using AvatarRepository
         */
        this.avatarSelector = new Selector<>(AvatarRepository.readAvailable());
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // MonstroclasseMemory - EVENTS SUBSCRIPTION
    // ***************************************************************

    @Override
    public void onSqlPerform(Object[] entities, SqlEventType sqlEventType)
    {
        switch (entities[0])
        {
            case Pupil ignored ->
            {
                this.pupils = PupilRepository.readAll();
                this.pupils.sort(Comparator.comparing(Pupil::getName));
            }
            case Avatar ignored -> this.avatarSelector = new Selector<>(AvatarRepository.readAvailable());
            case null, default -> Main.logger.warn("Embedded Memory doesn't operate with this entity " + entities[0] + " !");
        }
    }

    // ***************************************************************
    // END
    // ***************************************************************

    // ***************************************************************
    // MonstroclasseEmbeddedMemory - METHODS
    // ***************************************************************

    /**
     * Get all pupils of the database
     * @return List<Pupil>
     */
    public List<Pupil> getPupils()
    {
        return this.pupils;
    }

    /**
     * Get all avatars available of the database
     * @return Selector<Avatar>
     */
    public Selector<Avatar> getAvatarSelector()
    {
        return this.avatarSelector;
    }

    /**
     * Singleton
     * @return MonstroclasseMemory
     */
    public static MonstroclasseMemory getInstance()
    {
        return INSTANCE;
    }

    // ***************************************************************
    // END
    // ***************************************************************
}