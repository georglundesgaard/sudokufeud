package no.lundesgaard.sudokufeud.repository.hazelcast;

//@Repository
public class HazelcastGameRepository {} /* extends AbstractHazelcastRepository<Game> implements GameRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastGameRepository.class);
	private static final String GAME_REPOSITORY_MAP_ID = "gameRepositoryMap";

	public HazelcastGameRepository() {
		super(Game.class, LOGGER, GAME_REPOSITORY_MAP_ID);
	}

	@Override
	public List<Game> findAllByPlayer(Profile playerProfile) {
		Map<String, Game> repositoryMap = getRepositoryMap();
		return repositoryMap
				.values()
				.parallelStream()
				.filter((g) -> g.isPlayer(playerProfile))
				.collect(toList());
	}

	@Override
	public Game findOneByPlayer(Profile playerProfile, String gameId) {
		Game game = read(gameId);
		if (game == null || !game.isPlayer(playerProfile)) {
			throw new GameNotFoundException(gameId, playerProfile.getUserId());
		}

		return game;
	}

	@Override
	protected EntityNotFoundException entityNotFoundException(String id) {
		return new GameNotFoundException(id);
	}
}*/
