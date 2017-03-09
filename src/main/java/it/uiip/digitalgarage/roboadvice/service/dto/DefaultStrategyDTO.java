package it.uiip.digitalgarage.roboadvice.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class DefaultStrategyDTO {
	
	@NotNull
	private String name;
	
	@NotNull
	@Size(min = 1)
	private List<AssetClassStrategyDTO> list;

	public DefaultStrategyDTO() {
	}

	public String getName() {
		return this.name;
	}

	public List<AssetClassStrategyDTO> getList() {
		return this.list;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setList(List<AssetClassStrategyDTO> list) {
		this.list = list;
	}

	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof DefaultStrategyDTO)) return false;
		final DefaultStrategyDTO other = (DefaultStrategyDTO) o;
		if (!other.canEqual((Object) this)) return false;
		final Object this$name = this.getName();
		final Object other$name = other.getName();
		if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
		final Object this$list = this.getList();
		final Object other$list = other.getList();
		if (this$list == null ? other$list != null : !this$list.equals(other$list)) return false;
		return true;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $name = this.getName();
		result = result * PRIME + ($name == null ? 43 : $name.hashCode());
		final Object $list = this.getList();
		result = result * PRIME + ($list == null ? 43 : $list.hashCode());
		return result;
	}

	protected boolean canEqual(Object other) {
		return other instanceof DefaultStrategyDTO;
	}

	public String toString() {
		return "it.uiip.digitalgarage.roboadvice.service.dto.DefaultStrategyDTO(name=" + this.getName() + ", list=" + this.getList() + ")";
	}
}
